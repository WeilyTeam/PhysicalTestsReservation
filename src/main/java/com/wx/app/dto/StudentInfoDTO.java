package com.wx.app.dto; /**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        @ExcelProperty("年级")
     */

    private String grade;


    /*
        @ExcelProperty("学院")
     */

    private String specialtyClass;

    //private String college;

    /*
        @ExcelProperty("性别")
     */

    private String sex;
    private Integer type;
}
