package com.wx.app.entity;

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
    private Long id;

    /**
     *
     */
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
