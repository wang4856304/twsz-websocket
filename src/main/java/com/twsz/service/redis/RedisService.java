package com.twsz.service.redis;

import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.Map;

public interface RedisService {
    boolean set(String key, String value);
    boolean set(String key, String value, int expire);
    boolean hessianSet(String key, Object value);
    boolean hessianSet(String key, Object value, int expire);
    boolean hSet(String key, String filed, String value);
    boolean hSet(String key, String filed, Object value);
    String get(String key);
    <T> T hessianGet(String key);
    String hGet(String key, String filed);
    <T> T hessianHGet(String key, String filed);
    boolean del(String key);
    boolean hessianDel(String key);
    boolean incr(String key);
    boolean decr(String key);

    void publish(String channel, String msg);//发布
    void subscribe(JedisPubSub jedisPubSub, String channel);//订阅

    void lpush(String key, String value);//入队列
    List<String> blpop(int timeout, String key);//出队列
}
