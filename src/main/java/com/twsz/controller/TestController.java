package com.twsz.controller;

import com.twsz.entity.Response;
import com.twsz.enums.redis.RedisChannelEnum;
import com.twsz.service.redis.RedisService;
import com.twsz.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/test")
    public Object test(String msg) {
        //redisService.publish(RedisChannelEnum.TEST.getProdChannel(), msg);
        //redisService.publish(RedisChannelEnum.TEST1.getProdChannel(), msg);
        redisService.lpush("TEST", "hello world");
        return buildResponse(new Response());
    }
}
