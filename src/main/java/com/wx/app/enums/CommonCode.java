package com.wx.app.enums;



public enum CommonCode {
    /**
     * 操作成功
     */
    SUCCESS(200,"操作成功"),

    /**
     * 登录成功
     */
    SUCCESS_LOGIN(200,"登录成功"),

    /**
     * 注销成功
     */
    SUCCESS_LOGOUT(200,"注销成功"),

    /**
     * 注册失败，该用户已存在
     */
    SUCCESS_REGISTRATION(200,"注册成功"),

    /**
     * 操作失败
     */
    FAILURE(500,"操作失败"),

    /**
     * 参数验证失败
     */
    VALIDATE_FAILED(404,"参数验证失败"),

    /**
     * 注册失败，该用户已存在
     */
    FAILED_REGISTRATION(406,"注册失败，该用户已存在"),

    /**
     * 未登录或token已过期
     */
    UNAUTHORIZED(401,"认证失败请重新登录"),

    /**
     * 没有操作权限
     */
    FORBIDDEN(403,"没有操作权限"),

    /**
     * token非法
     */
    TOKEN_ILLEGAL(403,"token非法");

    private Integer code;
    private String msg;

    private CommonCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return this.code;
    }


    public String getmsg() {
        return this.msg;
    }
}

