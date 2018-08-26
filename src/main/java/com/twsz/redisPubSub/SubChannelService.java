package com.twsz.redisPubSub;

import com.twsz.enums.redis.RedisChannelEnum;
import com.twsz.exception.BusinessException;
import com.twsz.service.redis.RedisService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class SubChannelService {

    private static Log log = LogFactory.getLog(SubChannelService.class);

    @Autowired
    private RedisService redisService;

    @PostConstruct
    public void init() {
        try {
            RedisChannelEnum[] values = RedisChannelEnum.values();
            for (RedisChannelEnum redisChannelEnum: values) {
                Class<?> clazz = redisChannelEnum.getClazz();
                Subscriber subscriber = (Subscriber)clazz.newInstance();
                redisService.subscribe(subscriber, redisChannelEnum.getProdChannel());
            }
        }
        catch (Exception e) {
            log.error("redis subscriber error" , e);
            throw new BusinessException("redis subscriber error" , e);
        }

     }
}
