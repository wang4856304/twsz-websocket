package com.twsz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class TwszWebsocketConfig implements WebSocketConfigurer {

    @Bean
    public TwszWebSocketHandler getTwszWebSocketHandler() {
        return new TwszWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(getTwszWebSocketHandler(), "/twszHandler/{ID}").setAllowedOrigins("*").addInterceptors(new TwszWebSocketInterceptor());
    }
}

