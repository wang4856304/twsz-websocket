package com.twsz.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import java.util.Map;

public class TwszWebSocketInterceptor implements HandshakeInterceptor {

    private static Log log = LogFactory.getLog(TwszWebSocketInterceptor.class);
    public static final String WEBSOCKET_USERID = "userId";
    public static final String GROUP = "group";

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            String url = serverHttpRequest.getURI().toString();
            int pos = url.indexOf(TwszWebsocketConfig.TWSZ_HANDLER);
            String pattern = url.substring(pos + TwszWebsocketConfig.TWSZ_HANDLER.length() + 1);
            String[] arr = pattern.split("&");
            String group = arr[0].split("=")[1];
            String userId = arr[1].split("=")[1];
            map.put(WEBSOCKET_USERID,userId);
            map.put(GROUP,group);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
