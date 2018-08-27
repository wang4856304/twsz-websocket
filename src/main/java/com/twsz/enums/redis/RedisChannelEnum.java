package com.twsz.enums.redis;

import com.twsz.redisPubSub.handler.NoticeAllHandler;
import com.twsz.redisPubSub.handler.NoticeOneHandler;
import com.twsz.redisPubSub.handler.TestHandler;

public enum RedisChannelEnum {
    NOTICE_ALL_USER("notice_all_user", "pre_notice_all_user", NoticeAllHandler.class),
    NOTICE_ONE_USER("notice_one_user", "pre_notice_one_user", NoticeOneHandler.class);



    private String prodChannel;
    private String preReleaseChannel;
    private Class<?> clazz;
    private RedisChannelEnum(String prodChannel, String preReleaseChannel, Class<?> clazz) {
        this.prodChannel = prodChannel;
        this.preReleaseChannel = preReleaseChannel;
        this.clazz = clazz;
    }

    public String getProdChannel() {
        return prodChannel;
    }

    public String getPreReleaseChannel() {
        return preReleaseChannel;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
