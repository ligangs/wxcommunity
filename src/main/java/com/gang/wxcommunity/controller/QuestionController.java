package com.gang.wxcommunity.controller;

import com.gang.wxcommunity.dto.QuestionDTO;
import com.gang.wxcommunity.service.QuestionService;
import com.gang.wxcommunity.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @ResponseBody
    @PostMapping("/question/{id}")
    public ResponseVo question(@PathVariable("id")Long id) {
        QuestionDTO questionDTO=questionService.findQuestionById(id);
        return ResponseVo.getSuccResponse(questionDTO);
    }
}
