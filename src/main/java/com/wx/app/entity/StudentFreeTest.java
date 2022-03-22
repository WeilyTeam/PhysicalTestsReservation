package com.wx.app.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @TableName student_free_test
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentFreeTest implements Serializable {
    /**
     *
     */
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
    private String semester;

    /**
     *
     */
    private String reason;

    /**
     *
     */
    private String isPass;

    private static final long serialVersionUID = 1L;

}
