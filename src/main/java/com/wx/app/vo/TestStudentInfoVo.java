package com.wx.app.vo;/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class TestStudentInfoVo implements Serializable {

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
    //@ColumnWidth(25)
    //@ExcelProperty("学院")
    //private String college;
    @ColumnWidth(15)
    @ExcelProperty("手机号")
    private String phone;

    @JsonSerialize(using = ToStringSerializer.class)
    @ExcelIgnore
    @JsonProperty("testUserId")
    private Long id;
}
