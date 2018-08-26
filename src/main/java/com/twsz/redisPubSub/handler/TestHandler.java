package com.twsz.redisPubSub.handler;

import com.twsz.redisPubSub.Subscriber;

public class TestHandler extends Subscriber {
    @Override
    public void execute(String msg) {
        System.out.println("接收到消息:" + msg);
    }
}
