package com.wx.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @TableName studetn_semester
 */
@TableName(value ="studetn_semester")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudetnSemester implements Serializable {
    /**
     *
     */
    @JsonProperty("semester")
    private String semesterName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
