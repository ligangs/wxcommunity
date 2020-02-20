package com.gang.wxcommunity.controller;


import com.gang.wxcommunity.dto.PublishReq;
import com.gang.wxcommunity.enums.ResponseCodeEnum;
import com.gang.wxcommunity.model.Question;
import com.gang.wxcommunity.model.User;
import com.gang.wxcommunity.service.QuestionService;
import com.gang.wxcommunity.service.UserService;
import com.gang.wxcommunity.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PublishController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @ResponseBody
    @PostMapping("/publish")
    public ResponseVo publish(@RequestBody PublishReq publishReq) {

        //根据token判断是否登录
        User user = userService.findUserByToken(publishReq.getToken());
        if(user==null){
            return ResponseVo.getErrResponse(ResponseCodeEnum.NOT_LOGIN.getCode(), ResponseCodeEnum.NOT_LOGIN.getDesc());
        }

        Question question = new Question();
        question.setTitle(publishReq.getTitle());
        question.setDescription(publishReq.getDescription());
        question.setCreator(user.getId());
        question.setId(publishReq.getId());
        questionService.addOrUpdate(question);
        return ResponseVo.getSuccResponse("发布成功");
    }
}
