package com.twsz.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class TwszWebSocketInterceptor implements HandshakeInterceptor {

    private static Log log = LogFactory.getLog(TwszWebSocketInterceptor.class);
    public static final String WEBSOCKET_USERID = "WEBSOCKET_USERID";

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            String ID = serverHttpRequest.getURI().toString().split("ID=")[1];
            //System.out.println("当前session的ID="+ID);
            ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = request.getServletRequest().getSession();
            map.put(WEBSOCKET_USERID,ID);
            //session.setAttribute(ID, session);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
