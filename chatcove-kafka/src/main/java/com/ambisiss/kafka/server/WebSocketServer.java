package com.ambisiss.kafka.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ambisiss.kafka.util.KafkaUtil;
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
@Component
@ServerEndpoint("/chat/{userId}")
public class WebSocketServer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    private static ApplicationContext applicationContext;

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
        logger.info("当前socket通道" + userId + "已加入连接！！！");
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
        logger.info("服务端发生了错误：" + error.getMessage());
    }

    /**
     * 关闭连接事件处理
     */
    @OnClose
    public void onClose() {
        if (websocketClients.containsKey(userId)) {
            websocketClients.remove(userId);
        }
        logger.info("当前socket通道" + userId + "已退出连接！！！");
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
        System.out.println(jsonObject);
        if ("ping".equals(message)) {
            //心跳
            session.getBasicRemote().sendText("pong");
        } else {
            //TODO 调用Kafka进行消息分发
            System.out.println("kafka发送消息");
//            sendMessage(message, session);
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
            JSONObject jsonObject = JSONObject.parseObject(message);
            String senderId = jsonObject.getString("sender_id");
            String receiverId = jsonObject.getString("receiver_id");

            //TODO 发送消息到kafka中
//            KafkaUtil
        }
    }

    /**
     * kafka发送消息监听事件
     *
     * @param message
     */
    public void kafkaReceiveMsg(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        //发送者ID
        String senderId = jsonObject.getString("sender_id");
        //接受者ID
        String receiverId = jsonObject.getString("receiver_id");
        if (websocketClients.get(receiverId) != null) {
            Session session = websocketClients.get(receiverId);
            //进行消息发送
            try {
                session.getBasicRemote().sendText(message);
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
