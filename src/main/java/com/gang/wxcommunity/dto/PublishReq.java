package com.gang.wxcommunity.dto;

import lombok.Data;

@Data
public class PublishReq {
    private Long id;
    private String title;
    private String description;
    private String token;
}
