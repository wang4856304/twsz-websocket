package com.twsz.service.redis.impl;

import com.twsz.enums.redis.RedisQueueEnum;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
@DependsOn("springContextUtil")
public class RedisQueueStart {

    @PostConstruct
    public void init() {
        RedisQueueEnum[] redisQueueEnums = RedisQueueEnum.values();
        for (RedisQueueEnum redisQueueEnum: redisQueueEnums) {
            redisQueueEnum.init();
        }
    }
}
