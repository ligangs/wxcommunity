package com.gang.wxcommunity.vo;

import com.gang.wxcommunity.enums.ResponseCodeEnum;
import org.springframework.util.StringUtils;

public class ResponseVo<T> {
    private boolean state; // 是否成功
    private String code; // 回执编号
    private String message; // 回执消息
    private T data; // 响应参数体

    public ResponseVo() {
        this.code = ResponseCodeEnum.SUCCESS.getCode();
        this.message = ResponseCodeEnum.SUCCESS.getDesc();
        this.state = Boolean.TRUE;
    }

    public ResponseVo(String code, String message) {
        this.code = code;
        this.message = message;
        this.state = ResponseCodeEnum.SUCCESS.getCode().equalsIgnoreCase(code)?Boolean.TRUE:Boolean.FALSE;
    }

    private ResponseVo(String code, String message, T body) {
        this(code, message);
        this.data = body;
    }

    /**
     * Tips: 返回成功消息
     * @param body 需要返回的消息体，若没有，可为空
     * @return ResponseVo
     */
    public static <T> ResponseVo<T> getSuccResponse(T body) {
        return new ResponseVo(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getDesc(), body);
    }

    public static  ResponseVo getSuccResponse() {
        return new ResponseVo();
    }

    /**
     * Tips: 判断响应是否成功
     * @return Boolean
     */
    public Boolean successCheck() {
        return ResponseCodeEnum.SUCCESS.getCode().equalsIgnoreCase(this.getCode());
    }

    /**
     * Tips: 返回错误消息
     * @param responseCode 需要返回的错误消息标记
     * @return ResponseVo
     */
    public static ResponseVo getErrResponse(ResponseCodeEnum responseCode) {
        return new ResponseVo(responseCode.getCode(), responseCode.getDesc());
    }

    /**
     * Tips: 返回错误消息
     * @param responseCode 需要返回的错误消息标记
     *@param desc 错误描述信息
     * @return ResponseVo
     */
    public static ResponseVo getErrResponse(ResponseCodeEnum responseCode,String desc) {
        return new ResponseVo(responseCode.getCode(), desc);
    }

    /**
     * Tips: 返回错误消息
     * @param responseMessage 需要返回的错误消息标记
     * @return ResponseVo
     */
    public static ResponseVo getErrResponse(String responseMessage) {
        return new ResponseVo(ResponseCodeEnum.SYSTEM_ERROR_MESSAGE.getCode(), responseMessage);
    }

    public static ResponseVo getErrResponse(String code, String message) {
        if(StringUtils.isEmpty(code)){
            code = ResponseCodeEnum.SYSTEM_ERROR_MESSAGE.getCode();
        }
        return new ResponseVo(code, message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
