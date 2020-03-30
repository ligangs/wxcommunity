package com.gang.wxcommunity.controller;

import com.gang.wxcommunity.dto.NotificationDTO;
import com.gang.wxcommunity.dto.TokenReq;
import com.gang.wxcommunity.enums.NotificationTypeEnum;
import com.gang.wxcommunity.enums.ResponseCodeEnum;
import com.gang.wxcommunity.model.User;
import com.gang.wxcommunity.service.NotificationService;
import com.gang.wxcommunity.service.QuestionService;
import com.gang.wxcommunity.service.UserService;
import com.gang.wxcommunity.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @ResponseBody
    @GetMapping("/notification/{id}")
    public ResponseVo profile(@PathVariable(name = "id") Long id, @RequestBody TokenReq tokenReq) {
        //根据token判断是否登录
        User user = userService.findUserByToken(tokenReq.getToken());
        if(user==null){
            return ResponseVo.getErrResponse(ResponseCodeEnum.NOT_LOGIN.getCode(), ResponseCodeEnum.NOT_LOGIN.getDesc());
        }
        NotificationDTO notificationDTO=notificationService.read(id, user);

        if (NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()) {
            return ResponseVo.getSuccResponse(questionService.findQuestionById(notificationDTO.getOuterId()));
        } else {
            return ResponseVo.getErrResponse("读取消息失败！");
        }
    }
}
