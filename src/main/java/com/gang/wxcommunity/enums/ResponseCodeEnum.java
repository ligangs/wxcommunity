package com.gang.wxcommunity.enums;

/**
 * 回执编码配置
 * @author zhaopeng
 */
public enum ResponseCodeEnum{

    SUCCESS("0000", "成功"),
    NOT_LOGIN("0001", "用户未登录"),

    ERROR_400("9999", "error.400"),
    ERROR_401("9999", "error.401"),
    ERROR_404("9999", "error.404"),
    ERROR_405("9999", "error.405"),
    ERROR_500("9999", "error.500"),

    //系统异常 9000
    SYSTEM_BUSINESS_ERROR("9000", "系统繁忙，请稍后再试"),
    SYSTEM_ERROR_MESSAGE("9999", "系统内部错误"),
    UID_ERROR_MESSAGE("9004", "UID获取异常"),

    //权限异常 3000
    AUTH_OUT_OF_DATE("3001", "暂无权限"),
    AUTH_CHANNEL_NO_PROVIDER("3000","暂不支持该认证通道"),

    //请求参数异常 2000
    PARMS_NOT_NULL("2000", "必传参数为空"),
    PARMS_ERROR("2001","参数错误"),
    PARAM_OUT_LENGTH("2001","字段超出长度"),


    //数据库异常  9001
    DATABASE_ERROR("9001", "数据库操作失败"),
    DATABASE_DUPLICATE_KEY_ERROR("9002", "数据库唯一性字段重复"),
    DATABASE_NULL_ERROR_MESSAGE("9003", "数据库暂无数据！"),

    //中间件异常
    REDIS_ERROR("9001", "Redis异常"),

    FILE_UPLOAD_FAILE("9001", "文件上传异常"),
    FILE_DOWNLOAD_FAILE("9001", "文件下载异常"),
    FILE_OUT_OF_SIZE("9000", "文件上传图片过大"),
    FILE_BASE64_ERROR("9001", "图片格式错误"),
    FILE_NULL_BASE65_ERROR("9001", "图片为空"),

    //三方服务异常
    HTTP_NET_ERROR("1000", "网络异常"),

    ;

    ResponseCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final String code;
    private final String desc;
    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
}

