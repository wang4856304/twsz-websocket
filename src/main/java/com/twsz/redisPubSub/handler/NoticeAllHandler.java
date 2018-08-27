package com.twsz.redisPubSub.handler;

import com.alibaba.fastjson.JSONObject;
import com.twsz.config.TwszWebSocketHandler;
import com.twsz.redisPubSub.Subscriber;
import com.twsz.utils.SpringContextUtil;
import org.springframework.web.socket.TextMessage;

public class NoticeAllHandler extends Subscriber {
    @Override
    public void execute(String msg) {
        TwszWebSocketHandler twszWebSocketHandler = SpringContextUtil.getBean("twszWebSocketHandler");
        JSONObject json = JSONObject.parseObject(msg);
        String group = json.getString("group");
        twszWebSocketHandler.sendMessageToAllUsers(group, new TextMessage(msg));
    }
}
