package com.wx.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @TableName student_test
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StudentTest implements Serializable {
    /**
     *
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String specialtyClass;

    private String semester;

    private Long testId;

    private static final long serialVersionUID = 1L;


}
