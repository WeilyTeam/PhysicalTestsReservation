package com.wx.app.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wx.app.entity.StudentTest;
import com.wx.app.entity.StudentTestInfo;
import com.wx.app.entity.TeacherInfo;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @TableName student_test_info
 */
@TableName(value ="student_test_info")
@Data
public class StudentTestInfoVo implements Serializable {

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

    private TeacherInfo teacherInfo;

    public StudentTestInfoVo(StudentTestInfo studentTestInfo) {
        this.id = studentTestInfo.getId();
        this.name = studentTestInfo.getName();
        this.location = studentTestInfo.getLocation();
        this.day = studentTestInfo.getDay();
        this.hour = studentTestInfo.getHour();
        this.store = studentTestInfo.getStore();
        this.orderNum = studentTestInfo.getOrderNum();
        this.version = studentTestInfo.getVersion();
        this.semester = studentTestInfo.getSemester();
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
