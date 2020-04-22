package com.gang.wxcommunity.controller;

import com.gang.wxcommunity.dto.QuestionDTO;
import com.gang.wxcommunity.dto.TokenReq;
import com.gang.wxcommunity.enums.ResponseCodeEnum;
import com.gang.wxcommunity.model.User;
import com.gang.wxcommunity.service.QuestionService;
import com.gang.wxcommunity.service.UserService;
import com.gang.wxcommunity.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/question/{id}")
    public ResponseVo question(@PathVariable("id")Long id) {
        questionService.incView(id);
        QuestionDTO questionDTO=questionService.findQuestionById(id);
        return ResponseVo.getSuccResponse(questionDTO);
    }

    @ResponseBody
    @PostMapping("/questionlist")
    public ResponseVo profile(@RequestBody TokenReq tokenReq){
        //根据token判断是否登录
        User user = userService.findUserByToken(tokenReq.getToken());
        if(user==null){
            return ResponseVo.getErrResponse(ResponseCodeEnum.INVALID_TOKEN.getCode(), ResponseCodeEnum.INVALID_TOKEN.getDesc());
        }
        return ResponseVo.getSuccResponse(questionService.getQuestionByUserId(user.getId()));
    }
}
