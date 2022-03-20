package com.wx.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName student_test_info
 */
@TableName(value ="student_test_info")
@Data
public class StudentTestInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;

    private String location;

    private String day;

    private String hour;

    private Integer store;

    private Integer orderNum;

    private Integer version;

    private String semester;

    private Long headid;

    private String delFlag;

    @TableField(exist = false)
    private Boolean isFull;

    @TableField(exist = false)
    private TeacherInfo teacherInfo;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
