package com.twsz;

import com.twsz.utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TwszWebsocketApp {

    public static void main(String args[]) {
        SpringApplication.run(TwszWebsocketApp.class, args);
    }
}
