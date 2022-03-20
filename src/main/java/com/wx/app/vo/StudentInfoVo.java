package com.wx.app.vo;/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wx.app.entity.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class StudentInfoVo implements Serializable {

    @ColumnWidth(20)
    @ExcelProperty("学号")
    private String userName;
    @ColumnWidth(15)
    @ExcelProperty("姓名")
    private String name;
    @ColumnWidth(15)
    @ExcelProperty("班级")
    private String schoolClass;
    @ColumnWidth(10)
    @ExcelProperty("性别")
    private String sex;
    @ColumnWidth(15)
    @ExcelProperty("年级")
    private String grade;
    @ColumnWidth(25)
    @ExcelProperty("专业")
    private String specialty;
    @ColumnWidth(25)
    @ExcelProperty("学院")
    private String college;
    @ColumnWidth(15)
    @ExcelProperty("专业方向")
    private String specialtyDirection;

    @ExcelIgnore
    private LoginUser loginUser;
    @ExcelIgnore
    private Integer infoId;
    @ExcelIgnore
    private Integer userId;
}
