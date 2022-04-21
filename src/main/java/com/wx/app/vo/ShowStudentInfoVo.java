package com.wx.app.vo;/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wx.app.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class ShowStudentInfoVo implements Serializable {

    @ColumnWidth(20)
    @ExcelProperty("学号")
    private String userName;
    @ColumnWidth(15)
    @ExcelProperty("姓名")
    private String name;
    @ColumnWidth(10)
    @ExcelProperty("性别")
    private String sex;
    @ColumnWidth(15)
    @ExcelProperty("年级")
    private String grade;
    @ColumnWidth(25)
    @ExcelProperty("专业班级")
    private String specialtyClass;
    @ColumnWidth(25)
    @ExcelProperty("手机号")
    private String phone;


    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelIgnore
    private Long id;

    public ShowStudentInfoVo(User user) {
        this.userName = user.getUserName();
        this.name = user.getName();
        this.sex = user.getSex();
        this.grade = user.getGrade();
        this.specialtyClass = user.getSpecialtyClass();
        this.phone = user.getPhone();
        this.id = user.getId();
    }
}
