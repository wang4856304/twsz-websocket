package com.twsz.enums.redis;

import com.twsz.redisPubSub.handler.TestHandler;

public enum RedisChannelEnum {
    TEST("test", "pre_test", TestHandler.class),
    TEST1("test1", "pre_test", TestHandler.class),
    TEST2("test2", "pre_test", TestHandler.class),
    TEST3("test3", "pre_test", TestHandler.class),
    TEST4("test4", "pre_test", TestHandler.class),
    TEST5("test5", "pre_test", TestHandler.class),
    TEST6("test6", "pre_test", TestHandler.class),
    TEST7("test7", "pre_test", TestHandler.class),
    TEST8("test8", "pre_test", TestHandler.class),
    TEST9("test9", "pre_test", TestHandler.class),
    TEST10("test10", "pre_test", TestHandler.class),
    TEST11("test11", "pre_test", TestHandler.class),
    TEST12("test12", "pre_test", TestHandler.class),
    TEST13("test13", "pre_test", TestHandler.class),
    TEST14("test14", "pre_test", TestHandler.class);



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
