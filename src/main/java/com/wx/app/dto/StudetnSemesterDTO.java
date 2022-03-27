package com.wx.app.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @TableName studetn_semester
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudetnSemesterDTO implements Serializable {
    /**
     *
     */
    private String preSemesterName;
    private String newSemesterName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
