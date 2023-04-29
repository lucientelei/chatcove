package com.ambisiss.websocket.server;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: chenxiaoye
 * @Description:
 * @Data: 2023-4-27 20:56:57
 */
@Component
@ServerEndpoint("/websocket/{socketName}")
public class WebSocketServer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    private String socketName;

    /**
     * 发送消息到指定连接
     *
     * @param socketName 连接名
     * @param jsonString 消息
     */
    public static void sendMessage(String socketName, String jsonString) {
        Session currSession = websocketClients.get(socketName);
        if (currSession != null) {
            try {
                currSession.getBasicRemote().sendText(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * websocket连接事件处理
     *
     * @param socketName
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("socketName") String socketName, Session session) {
        this.socketName = socketName;
        this.session = session;
        if (websocketClients.get(socketName) == null) {
            websocketClients.put(socketName, session);
            logger.info("当前socket通道" + socketName + "已加入连接！！！");
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
        logger.info("服务端发生了错误：" + error.getMessage());
    }

    /**
     * 关闭连接事件处理
     */
    @OnClose
    public void onClose() {
        websocketClients.remove(socketName);
        logger.info("当前socket通道" + socketName + "已退出连接！！！");
    }

    /**
     * 收到客户端的消息
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("收到了消息：" + message);
        session.getAsyncRemote().sendText("来自服务器：" + this.socketName + "的消息收到了");
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
