package com.wx.app.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 *
 * @TableName teacher_info
 */
@TableName(value ="teacher_info")
@Data
public class TeacherInfo implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String sex;

    /**
     *
     */
    private String college;

    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long phoneNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
