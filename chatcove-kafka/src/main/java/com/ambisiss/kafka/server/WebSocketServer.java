package com.ambisiss.kafka.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ambisiss.common.constant.MessageReadConstant;
import com.ambisiss.common.vo.ChGroupMembersVo;
import com.ambisiss.kafka.constant.KafkaConstant;
import com.ambisiss.kafka.util.KafkaUtil;
import com.ambisiss.mongodb.dto.ChChatMsgInsertDto;
import com.ambisiss.mongodb.dto.ChGroupMsgInsertDto;
import com.ambisiss.mongodb.entity.ChChatMessageMongo;
import com.ambisiss.mongodb.entity.ChGroupMessageMongo;
import com.ambisiss.mongodb.service.ChChatMessageMongoService;
import com.ambisiss.mongodb.service.ChGroupMessageMongoService;
import com.ambisiss.mongodb.service.impl.ChChatMessageMongoServiceImpl;
import com.ambisiss.system.entity.ChGroupMembers;
import com.ambisiss.system.service.ChGroupMembersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author: chenxiaoye
 * @Description: userId:用户全局唯一ID
 * @Data: 2023-4-27 20:56:57
 */
@Slf4j
@Component("WebSocketServer")
@ServerEndpoint("/chat/{userId}")
public class WebSocketServer {

    private static ChChatMessageMongoService chatMongoService;

    private static ChGroupMessageMongoService groupMongoService;

    private static ChGroupMembersService groupMembersService;

    @Autowired
    public void setMessageMongoService(ChChatMessageMongoService chatMongoService,
                                       ChGroupMessageMongoService groupMongoService,
                                       ChGroupMembersService groupMembersService) {
        WebSocketServer.chatMongoService = chatMongoService;
        WebSocketServer.groupMongoService = groupMongoService;
        WebSocketServer.groupMembersService = groupMembersService;
    }

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
     */
    private static int onlineCount = 0;

    /**
     * 以通道名称为key，连接会话为对象保存起来
     */
    public static Map<Long, Session> websocketClients = new ConcurrentHashMap<Long, Session>();

    /**
     * 会话
     */
    private Session session;

    /**
     * 通道名称
     */
    private Long userId;

    /**
     * websocket连接事件处理
     *
     * @param userId
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("userId") Long userId, Session session) {
        this.userId = userId;
        this.session = session;
        websocketClients.put(userId, session);
        onlineCount++;
        log.info("当前socket通道" + userId + "已加入连接！！！");
        //TODO 查询所有未读信息
        ChChatMessageMongo chatMessageMongo = chatMongoService.listUnReadByUserId(userId);
        ChGroupMessageMongo groupMessageMongo = groupMongoService.listUnReadByUserId(userId);
        if (chatMessageMongo != null && chatMessageMongo.getChatMessageList() != null) {
            chatMessageMongo.getChatMessageList().forEach(item -> {
                synchronized (session) {
                    try {
                        session.getBasicRemote().sendText(item.toString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        if (groupMessageMongo != null && groupMessageMongo.getGroupMessageList() != null) {
            groupMessageMongo.getGroupMessageList().forEach(item -> {
                synchronized (session) {
                    try {
                        session.getBasicRemote().sendText(item.toString());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    /**
     * 发生错误事件处理
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
        log.info("服务端发生了错误：" + error.getMessage());
    }

    /**
     * 关闭连接事件处理
     */
    @OnClose
    public void onClose() {
        if (websocketClients.containsKey(userId)) {
            websocketClients.remove(userId);
        }
        onlineCount--;
        log.info("当前socket通道" + userId + "已退出连接！！！");
    }

    /**
     * 收到客户端的消息
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        if ("ping".equals(message)) {
            //心跳
            session.getBasicRemote().sendText("pong");
        } else {
            //TODO 调用Kafka进行消息分发
            kafkaSendMessage(message, session);
        }
    }

    /**
     * 发送消息到kafka
     *
     * @param message
     * @param session
     */
    public void kafkaSendMessage(String message, Session session) {
        if (!StringUtils.isEmpty(message)) {
            //TODO 发送消息到kafka中
            JSONObject jsonObject = JSONObject.parseObject(message);
            String groupId = jsonObject.getString("groupId");
            //私聊
            if (StringUtils.isEmpty(groupId)) {
                log.info(userId + ":kafka发送私聊消息");
                KafkaUtil.sendSyncMsg(KafkaConstant.PERSONAL_TOPIC, String.valueOf(jsonObject));
            } else {
                //群聊
                log.info(userId + ":kafka发送群聊消息");
                KafkaUtil.sendSyncMsg(KafkaConstant.PERSONAL_TOPIC, String.valueOf(jsonObject));
            }
        }
    }

    /**
     * 服务端返回信息给用户端
     *
     * @param dto
     */
    public void kafkaPersonalReceiveMsg(ChChatMsgInsertDto dto) {
        Long receiverId = dto.getReceiverId();
        if (websocketClients.get(receiverId) != null) {
            Session session = websocketClients.get(receiverId);
            //进行消息发送
            try {
                session.getBasicRemote().sendText(dto.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //TODO 更新mongodb已读状态
            chatMongoService.updateRead(dto.getUserId(), dto.getMessageUuid(), MessageReadConstant.READ);
        }
    }

    /**
     * 服务端返回信息给用户端
     *
     * @param
     */
    public void kafkaGroupReceiveMsg(ChGroupMsgInsertDto dto) {
        //获取群组ID
        Long groupId = dto.getGroupId();
        //TODO 查询群聊成员，查看成员在线则发送消息
        List<ChGroupMembersVo> membersVos = groupMembersService.listMember(groupId);
        //获取成员用户ID列表
        List<Long> memberIds = membersVos.stream().map(ChGroupMembersVo::getMemberId).collect(Collectors.toList());
        memberIds.forEach(item -> {
            if (websocketClients.get(item) != null) {
                Session session = websocketClients.get(item);
                session.getAsyncRemote().sendText(dto.toString());
                //TODO 更新已读状态
                groupMongoService.updateRead(item, dto.getMessageUuid(), MessageReadConstant.READ);
            }
        });
    }

    /**
     * 发送消息到指定连接
     *
     * @param userId     连接名
     * @param jsonString 消息
     */
    public static void sendMessage(Long userId, String jsonString) {
        Session currSession = websocketClients.get(userId);
        if (currSession != null) {
            currSession.getAsyncRemote().sendText(jsonString);
        }
    }

    /**
     * 向所有连接发送消息
     *
     * @param jsonObject
     */
    public void sendMessageAll(JSONObject jsonObject) {
        for (Session item : websocketClients.values()) {
            item.getAsyncRemote().sendText(jsonObject.toJSONString());
        }
    }
}
