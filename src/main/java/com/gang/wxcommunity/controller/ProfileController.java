package com.gang.wxcommunity.controller;

import com.gang.wxcommunity.dto.ProfileDTO;
import com.gang.wxcommunity.dto.TokenReq;
import com.gang.wxcommunity.enums.ResponseCodeEnum;
import com.gang.wxcommunity.model.User;
import com.gang.wxcommunity.service.NotificationService;
import com.gang.wxcommunity.service.QuestionService;
import com.gang.wxcommunity.service.UserService;
import com.gang.wxcommunity.vo.ResponseVo;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/profile")
    public ResponseVo getCount(@RequestBody TokenReq tokenReq){
        //根据token判断是否登录
        User user = userService.findUserByToken(tokenReq.getToken());
        if(user==null){
            return ResponseVo.getErrResponse(ResponseCodeEnum.NOT_LOGIN.getCode(), ResponseCodeEnum.NOT_LOGIN.getDesc());
        }
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setMyQuestionCount(questionService.getMyQuestionCount(user.getId()));
        profileDTO.setUnReadCount(notificationService.unreadCount(user.getId()));

        return ResponseVo.getSuccResponse(profileDTO);
    }

    @ResponseBody
    @PostMapping("/profile/{action}")
    public ResponseVo profile(@PathVariable(name="action")String action, @RequestBody TokenReq tokenReq){
        //根据token判断是否登录
        User user = userService.findUserByToken(tokenReq.getToken());
        if(user==null){
            return ResponseVo.getErrResponse(ResponseCodeEnum.NOT_LOGIN.getCode(), ResponseCodeEnum.NOT_LOGIN.getDesc());
        }
        if ("questions".equals(action)) {
            //得到对应用户的问题
            return ResponseVo.getSuccResponse(questionService.getQuestionByUserId(user.getId()));
        } else if ("replies".equals(action)) {
            //得到用户得消息列表
            ;return ResponseVo.getSuccResponse(notificationService.list(user.getId()));
        }
        return ResponseVo.getSuccResponse();
    }
}
