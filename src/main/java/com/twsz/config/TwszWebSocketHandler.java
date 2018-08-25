package com.twsz.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TwszWebSocketHandler implements WebSocketHandler {

    private static final ConcurrentMap<String, WebSocketSession> userMap = new ConcurrentHashMap<>();
    private static Log log = LogFactory.getLog(TwszWebSocketHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        String userId = webSocketSession.getUri().toString().split("ID=")[1];
        log.info("用户id为" + userId + "建立连接成功!");
        if (!StringUtils.isEmpty(userId)) {
            userMap.put(userId, webSocketSession);
            webSocketSession.sendMessage(new TextMessage("连接建立成功!"));
        }
        log.info("当前在线人数为:" + userMap.size());
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        String message = webSocketMessage.getPayload().toString();
        JSONObject jsonObject = JSONObject.parseObject(message);
        sendMessageToAllUsers(new TextMessage("hello world"));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        log.error("连接出错!");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        String userId = (String) webSocketSession.getAttributes().get(TwszWebSocketInterceptor.WEBSOCKET_USERID);
        userMap.remove(userId);
        log.info("用户id为" + userId + "连接已关闭!");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 发送信息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String clientId, TextMessage message) {
        if (userMap.get(clientId) == null) return false;
        WebSocketSession session = userMap.get(clientId);
        //System.out.println("sendMessage:" + session);
        if (!session.isOpen()) {
            return false;
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 广播信息
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<String> clientIds = userMap.keySet();
        WebSocketSession session = null;
        for (String clientId : clientIds) {
            try {
                session = userMap.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }

        return  allSendSuccess;
    }

}
