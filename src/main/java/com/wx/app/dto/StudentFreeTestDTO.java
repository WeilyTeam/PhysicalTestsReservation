package com.wx.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @TableName student_free_test
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentFreeTestDTO implements Serializable {

    private String semester;

    private String reason;
    private Integer type;
    private String remark;

    private List<String> images;

    private static final long serialVersionUID = 1L;

}
