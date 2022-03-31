package com.wx.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 用户表(User)实体类
 *
 * @author 三更
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;

    /**
    * 主键
    */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String userName;
    private String name;
    private String password;
    private String identity;
    private String schoolClass;
    private String sex;
    private String grade;
    private String specialty;
    private String college;
    private String specialtyDirection;
    private String phone;
}
