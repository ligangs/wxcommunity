package com.gang.wxcommunity.mapper;

import com.gang.wxcommunity.model.Comment;

public interface CommentExtMapper {
    //增加评论数
    int incComment(Comment record);
}
