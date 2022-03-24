package com.wx.app.dto; /**
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
public class StudentInfoDTO implements Serializable {


    /*
        @ExcelProperty("学号")
     */
    private String userName;

    /*
        @ExcelProperty("姓名")
     */
    private String name;

    /*
        @ExcelProperty("班级")
     */
    private String schoolClass;

    /*
        @ExcelProperty("性别")
     */
    private String sex;

    /*
        @ExcelProperty("年级")
     */
    private String grade;

    /*
      *  @ExcelProperty("专业")
     */
    private String specialty;

    /*
        @ExcelProperty("学院")
     */
    private String college;

    /*
        @ExcelProperty("专业方向")
     */
    private String specialtyDirection;
}
