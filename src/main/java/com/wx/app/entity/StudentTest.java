package com.wx.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
    private Long id;

    private Long userId;

    private String semester;

    private Long testId;

    private static final long serialVersionUID = 1L;


}
