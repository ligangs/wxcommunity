package com.gang.wxcommunity.enums;

public enum CommentTypeEnum {
    QUESTION(1),//回答问题
    COMMENT(2);//回复评论
    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }


    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value. getType()== type) {
                return true;
            }
        }
        return false;
    }
}
