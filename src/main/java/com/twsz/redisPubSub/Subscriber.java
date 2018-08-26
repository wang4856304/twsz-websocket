package com.twsz.redisPubSub;

import redis.clients.jedis.JedisPubSub;

public abstract class Subscriber extends JedisPubSub {

    public abstract void execute(String msg);

    @Override
    public void onMessage(String channel, String message) {       //收到消息会调用
        System.out.println("channel=" + channel);
        execute(message);
    }
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {    //订阅了频道会调用
        System.out.println(String.format("subscribe redis channel success, channel %s, subscribedChannels %d",
                channel, subscribedChannels));
    }
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {   //取消订阅 会调用
        System.out.println(String.format("unsubscribe redis channel, channel %s, subscribedChannels %d",
                channel, subscribedChannels));

    }
}
