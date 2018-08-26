package com.twsz.service.redis.impl;

import com.twsz.exception.BusinessException;
import com.twsz.service.redis.RedisService;
import com.twsz.utils.Hessian2Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private JedisPool jedisPool;

    private static final String OK = "OK";

    @Override
    public boolean set(String key, String value) {
        Jedis jedis = getJedis();
        String result = jedis.set(key, value);
        if (OK.equals(result)) {
            close(jedis);
            return true;
        }
        close(jedis);
        return false;
    }

    @Override
    public boolean set(String key, String value, int expire) {
        Jedis jedis = getJedis();
        String result = jedis.setex(key, expire, value);
        if (OK.equals(result)) {
            close(jedis);
            return true;
        }
        return false;
    }

    @Override
    public boolean hessianSet(String key, Object value) {
        Jedis jedis = getJedis();
        String result = jedis.set(key.getBytes(), Hessian2Util.serializable(value));
        if (OK.equals(result)) {
            close(jedis);
            return true;
        }
        close(jedis);
        return false;
    }

    @Override
    public boolean hessianSet(String key, Object value, int expire) {
        Jedis jedis = getJedis();
        String result = jedis.setex(key.getBytes(), expire, Hessian2Util.serializable(value));
        if (OK.equals(result)) {
            close(jedis);
            return true;
        }
        close(jedis);
        return false;
    }

    @Override
    public boolean hSet(String key, String filed, String value) {
        Jedis jedis = getJedis();
        jedis.hset(key, filed, value);
        close(jedis);
        return true;
    }

    @Override
    public boolean hSet(String key, String filed, Object value) {
        Jedis jedis = getJedis();
        jedis.hset(key.getBytes(), filed.getBytes(), Hessian2Util.serializable(value));
        return true;
    }

    @Override
    public String get(String key) {
        Jedis jedis = getJedis();
        if (!check(jedis, key)) {
            return null;
        }
        String value = jedis.get(key);
        close(jedis);
        return value;
    }

    @Override
    public <T> T hessianGet(String key) {
        Jedis jedis = getJedis();
        if (!check(jedis, key.getBytes())) {
            return null;
        }
        byte[] bytes = jedis.get(key.getBytes());
        Object value = Hessian2Util.deserializable(bytes);
        close(jedis);
        return (T)value;
    }

    @Override
    public String hGet(String key, String filed) {
        Jedis jedis = getJedis();
        if (!check(jedis, key)) {
            return null;
        }
        String value = jedis.hget(key, filed);
        close(jedis);
        return value;
    }

    @Override
    public <T> T hessianHGet(String key, String filed) {
        Jedis jedis = getJedis();
        if (!check(jedis, key.getBytes())) {
            return null;
        }
        byte[] bytes = jedis.hget(key.getBytes(), filed.getBytes());
        Object value = Hessian2Util.deserializable(bytes);
        close(jedis);
        return (T)value;
    }

    @Override
    public boolean del(String key) {
        Jedis jedis = getJedis();
        if (!check(jedis, key)) {
            return true;
        }
        jedis.del(key);
        close(jedis);
        return true;
    }

    @Override
    public boolean hessianDel(String key) {
        Jedis jedis = getJedis();
        if (!check(jedis, key.getBytes())) {
            return true;
        }
        jedis.del(key.getBytes());
        close(jedis);
        return true;
    }

    @Override
    public boolean incr(String key) {
        Jedis jedis = getJedis();
        jedis.incr(key);
        close(jedis);
        return true;
    }

    @Override
    public boolean decr(String key) {
        Jedis jedis = getJedis();
        jedis.decr(key);
        close(jedis);
        return true;
    }

    @Override
    public void publish(String channel, String msg) {
        Jedis jedis = getJedis();
        jedis.publish(channel, msg);
        close(jedis);
    }

    @Override
    public void subscribe(JedisPubSub jedisPubSub, String channel) {
        Jedis jedis = getJedis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jedis.subscribe(jedisPubSub, channel);
                }
                catch (Exception e) {
                    close(jedis);
                    throw new BusinessException("redis订阅失败", e);
                }

            }
        }).start();
    }

    @Override
    public void lpush(String key, String value) {
        Jedis jedis = getJedis();
        jedis.lpush(key, value);
        close(jedis);
    }

    @Override
    public List<String> blpop(int timeout, String key) {
        Jedis jedis = getJedis();
        List<String> values = jedis.blpop(timeout, key);
        close(jedis);
        return values;
    }

    private Jedis getJedis() {
        return jedisPool.getResource();
    }
    private void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
    private boolean check(Jedis jedis,String key) {
        return jedis.exists(key);
    }
    private boolean check(Jedis jedis,byte[] key) {
        return jedis.exists(key);
    }
}
