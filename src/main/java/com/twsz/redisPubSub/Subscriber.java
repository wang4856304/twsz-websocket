package com.twsz.redisPubSub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.JedisPubSub;

public abstract class Subscriber extends JedisPubSub {

    private static Log log = LogFactory.getLog(Subscriber.class);

    public abstract void execute(String msg);

    @Override
    public void onMessage(String channel, String message) {       //收到消息会调用
        log.info("channel=" + channel);
        execute(message);
    }
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {    //订阅了频道会调用
        log.info(String.format("subscribe redis channel success, channel %s, subscribedChannels %d",
                channel, subscribedChannels));
    }
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {   //取消订阅 会调用
        log.info(String.format("unsubscribe redis channel, channel %s, subscribedChannels %d",
                channel, subscribedChannels));
    }
}
