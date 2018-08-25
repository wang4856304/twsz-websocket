package com.twsz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.twsz.config.TwszWebSocketHandler;
import com.twsz.entity.Response;
import com.twsz.service.BaseService;
import com.twsz.service.SendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;

@Service
public class SendMsgServiceImpl extends BaseService implements SendMsgService {

    @Autowired
    private TwszWebSocketHandler twszWebSocketHandler;

    @Override
    public Response sendMsg(String group, String userId, JSONObject data) {
        if (StringUtils.isEmpty(group)) {
            return buildErrorResponse("222", "group为空");
        }
        if (StringUtils.isEmpty(userId)) {
            twszWebSocketHandler.sendMessageToAllUsers(group, new TextMessage(data.toJSONString()));
        }
        else {
            twszWebSocketHandler.sendMessageToUser(userId, new TextMessage(data.toJSONString()));
        }
        return buildSuccesResponse();
    }
}
