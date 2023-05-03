package com.ambisiss.kafka.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ambisiss.common.utils.JwtUtils;
import com.ambisiss.kafka.constant.KafkaConstant;
import com.ambisiss.kafka.util.KafkaUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: chenxiaoye
 * @Description: userId:用户全局唯一ID
 * @Data: 2023-4-27 20:56:57
 */
@Slf4j
@Component
@ServerEndpoint("/chat/{userId}")
public class WebSocketServer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
     */
    private static int onlineCount = 0;

    /**
     * 以通道名称为key，连接会话为对象保存起来
     */
    public static Map<String, Session> websocketClients = new ConcurrentHashMap<String, Session>();

    /**
     * 会话
     */
    private Session session;

    /**
     * 通道名称
     */
    private String userId;

    /**
     * websocket连接事件处理
     *
     * @param userId
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        this.userId = userId;
        this.session = session;
        websocketClients.put(userId, session);
        log.info("当前socket通道" + userId + "已加入连接！！！");
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
        JSONObject jsonObject = JSONObject.parseObject(message);
        log.info(String.valueOf(jsonObject));
        if ("ping".equals(message)) {
            //心跳
            session.getBasicRemote().sendText("pong");
        } else {
            //TODO 调用Kafka进行消息分发
            sendMessage(message, session);
        }
    }

    /**
     * 发送消息到kafka
     *
     * @param message
     * @param session
     */
    public void sendMessage(String message, Session session) {
        if (!StringUtils.isEmpty(message)) {
            //TODO 发送消息到kafka中
            JSONObject jsonObject = JSONObject.parseObject(message);
            jsonObject.put("sessionId", session.getId());
            String groupId = jsonObject.getString("groupId");
            //私聊
            if (StringUtils.isEmpty(groupId)) {
                log.info(session.getId() + "kafka发送私聊消息");
                KafkaUtil.sendSyncMsg(KafkaConstant.PERSONAL_TOPIC, String.valueOf(jsonObject));
            } else {
                //群聊
                log.info(session.getId() + "kafka发送群聊消息");
                KafkaUtil.sendSyncMsg(KafkaConstant.PERSONAL_TOPIC, String.valueOf(jsonObject));
            }
        }
    }

    /**
     * 服务端返回信息给用户端
     *
     * @param message
     */
    public void kafkaReceiveMsg(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        //发送者ID
        String senderId = jsonObject.getString("senderId");
        if (websocketClients.get(senderId) != null) {
            Session session = websocketClients.get(senderId);
            //进行消息发送
            try {
                log.info("kafkaReceiveMsg 返回websocket:" + message);
//                session.getBasicRemote().sendText(message);
                session.getBasicRemote().sendText("kafkaReceiveMsg 返回websocket");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            try {
                currSession.getBasicRemote().sendText(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
