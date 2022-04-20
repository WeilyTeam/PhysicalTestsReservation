package com.wx.app.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wx.app.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
@ContentRowHeight(20)
@HeadRowHeight(20)
public class StudentFreeTestVo implements Serializable {

    public StudentFreeTestVo(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.userName = user.getUserName();
        this.sex = user.getSex();
        this.grade = user.getGrade();
        this.idCard = user.getIdCard();
        this.birth = user.getBirth();
        this.nationality = user.getNationality();
        this.specialtyClass = user.getSpecialtyClass();
    }

    @ExcelIgnore
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ColumnWidth(10)
    @ExcelProperty("姓名")
    private String name;

    @ColumnWidth(10)
    @ExcelProperty("学号")
    @TableField(exist = false)
    private String userName;


    @ColumnWidth(15)
    @ExcelProperty("学期")
    private String semester;


    @ColumnWidth(20)
    @ExcelProperty("原因")
    private String reason;


    @ColumnWidth(8)
    @ExcelProperty("性别")
    private String sex;


    @ExcelProperty("年级")
    @ColumnWidth(20)
    private String grade;


    @ColumnWidth(20)
    @ExcelProperty("专业班级")
    private String specialtyClass;

    @ColumnWidth(20)
    @ExcelProperty("名族代码")
    private String nationality;

    @ColumnWidth(20)
    @ExcelProperty("生日")
    private String birth;

    @ColumnWidth(20)
    @ExcelProperty("身份证")
    private String idCard;

    @ColumnWidth(15)
    @ExcelProperty("手机号")
    private String phone;
    @ColumnWidth(15)
    @ExcelProperty("备注")
    private String remark;

    @ColumnWidth(15)
    @ExcelProperty("审批人")
    private String handler;

    @ExcelIgnore
    private String isPass;
    @ExcelIgnore
    private Integer isRead;

    @ExcelIgnore
    private Integer type;
    @ExcelIgnore
    @TableField(exist = false)
    private List<String> images;
    @ExcelIgnore
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
