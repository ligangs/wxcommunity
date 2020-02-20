package com.gang.wxcommunity.dto;

import com.gang.wxcommunity.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private User user;
}
