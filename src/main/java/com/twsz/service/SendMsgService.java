package com.twsz.service;

import com.alibaba.fastjson.JSONObject;
import com.twsz.entity.Response;

public interface SendMsgService {
    Response sendMsg(String group, String userId, JSONObject data);
}
