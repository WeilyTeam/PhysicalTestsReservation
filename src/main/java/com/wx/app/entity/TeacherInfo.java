package com.wx.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

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
    private String phone;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
