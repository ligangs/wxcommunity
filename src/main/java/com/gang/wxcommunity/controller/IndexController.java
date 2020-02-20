package com.gang.wxcommunity.controller;

import com.gang.wxcommunity.service.QuestionService;
import com.gang.wxcommunity.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @ResponseBody
    @PostMapping("/index")
    public ResponseVo publish() {
        return ResponseVo.getSuccResponse(questionService.findAll());
    }
}
