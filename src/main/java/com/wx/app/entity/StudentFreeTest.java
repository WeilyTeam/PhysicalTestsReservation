package com.wx.app.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wx.app.dto.StudentFreeTestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String semester;

    private String reason;

    private String isPass;
    private String auditTime;
    private String auditMessage;
    private Integer isRead;

    private static final long serialVersionUID = 1L;



    public StudentFreeTest(StudentFreeTestDTO studentFreeTestDTO, Long userId) {
        this.reason = studentFreeTestDTO.getReason();
        this.semester = studentFreeTestDTO.getSemester();
        this.userId = userId;
    }
}
