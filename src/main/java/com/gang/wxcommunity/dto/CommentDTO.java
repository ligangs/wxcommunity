package com.gang.wxcommunity.dto;

import com.gang.wxcommunity.model.User;
import lombok.Data;

import java.util.List;

@Data
public class CommentDTO {

    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String comment;
    private Integer commentCount;
    private User user;
    private Boolean isShowSon;//是否展开二级评论
    private List<CommentDTO> commentSon;
}
