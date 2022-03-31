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
 * @TableName student_test_info
 */
@TableName(value ="student_test_info")
@Data
public class StudentTestInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;

    private String location;

    private String day;

    private String hour;

    private Integer store;

    private Integer orderNum;

    private Integer version;

    private String semester;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long headid;

    private String delFlag;

    private String description;

    @TableField(exist = false)
    private Boolean isFull;

    @TableField(exist = false)
    private User teacherInfo;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
