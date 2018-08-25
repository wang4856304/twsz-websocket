package com.twsz.controller;


import com.alibaba.fastjson.JSONObject;
import com.twsz.entity.Response;
import com.twsz.service.SendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sendMsg")
public class SendMsgController extends BaseController {

    @Autowired
    private SendMsgService sendMsgService;

    @RequestMapping("/{group}/{userId}")
    public Object sendMsgToUser(@PathVariable(value = "group") String group, @PathVariable(value = "userId") String userId, @RequestBody JSONObject jsonObject) {
        Response response = sendMsgService.sendMsg(group, userId, jsonObject);
        return buildResponse(response);
    }

    @RequestMapping("/{group}")
    public Object sendMsgToAll(@PathVariable(value = "group") String group, @RequestBody JSONObject jsonObject) {
        Response response = sendMsgService.sendMsg(group, null, jsonObject);
        return buildResponse(response);
    }
}
