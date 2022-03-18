package com.wx.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
    private Long id;

    /**
     *
     */
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
    private Long phoneNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
