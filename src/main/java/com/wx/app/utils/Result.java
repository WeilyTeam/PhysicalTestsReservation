package com.wx.app.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wx.app.enums.CommonCode;
import lombok.ToString;


//@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Result<T> extends JSON {
    /**
     * 状态码
     */

    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */

    private String msg;
    /**
     * 查询到的结果数据，
     */

    private T data;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(CommonCode commonCode, T data) {
        this.code = commonCode.getCode();
        this.msg = commonCode.getmsg();
        this.data = data;
    }

    public Result(CommonCode commonCode) {
        this.code = commonCode.getCode();
        this.msg = commonCode.getmsg();
    }
}
