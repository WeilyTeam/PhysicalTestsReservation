package com.wx.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName student_test_info
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StudentTestInfo implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    private Date time;

    private String name;

    private String location;

    private Integer store;

    private Integer orderNum;

    @Version
    private Integer version;

    private String semester;

    private static final long serialVersionUID = 1L;

}
