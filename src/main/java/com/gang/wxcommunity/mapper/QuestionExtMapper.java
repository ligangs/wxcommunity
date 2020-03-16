package com.gang.wxcommunity.mapper;

import com.gang.wxcommunity.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    //阅读数加一
    int incView(Question record);

    //增加评论数
    int incComment(Question record);

    //得到相关问题列表
    List<Question> getRelatedQuestionList(Question record);
}
