package com.wx.app.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @TableName student_free_test
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode
@ContentRowHeight(20)
@HeadRowHeight(20)
public class StudentFreeTestVo implements Serializable {


    @ColumnWidth(10)
    @ExcelProperty("姓名")
    private String name;


    @ColumnWidth(15)
    @ExcelProperty("学期")
    private String semester;


    @ColumnWidth(20)
    @ExcelProperty("原因")
    private String reason;

    @ColumnWidth(20)
    @ExcelProperty("班级")
    private String schoolClass;


    @ColumnWidth(8)
    @ExcelProperty("性别")
    private String sex;


    @ExcelProperty("年级")
    @ColumnWidth(20)
    private String grade;


    @ExcelProperty("专业")
    @ColumnWidth(20)
    private String specialty;


    @ColumnWidth(20)
    @ExcelProperty("学院")
    private String college;


    @ColumnWidth(20)
    @ExcelProperty("专业方向")
    private String specialtyDirection;


    @ExcelIgnore
    private Long id;
    @ExcelIgnore
    private Long userId;
    @ExcelIgnore
    private String isPass;
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

}
