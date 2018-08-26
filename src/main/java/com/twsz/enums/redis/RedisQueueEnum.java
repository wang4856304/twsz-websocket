package com.twsz.enums.redis;

import com.twsz.service.redis.RedisService;
import com.twsz.utils.SpringContextUtil;

import java.util.List;
import java.util.concurrent.Executors;

public enum RedisQueueEnum {
    TEST("TEST") {
       public void init() {
           Executors.newFixedThreadPool(1).execute(new Runnable() {
               @Override
               public void run() {
                   while (true) {
                       List<String> values = redisService.blpop(10, "TEST");
                       if (values.size() == 2) {
                           System.out.println(values.get(1));
                       }
                   }
               }
           });
        }
    };

    private RedisQueueEnum(String queue) {
        this.queue = queue;
    }
    private String queue;

    public String getQueue() {
        return queue;
    }
    public abstract void init();

    RedisService redisService = SpringContextUtil.getBean("redisServiceImpl");
}
