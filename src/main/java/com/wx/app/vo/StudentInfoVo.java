package com.wx.app.vo;/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */

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
@Accessors(chain = true)
public class StudentInfoVo implements Serializable {
    private Integer infoId;
    private Integer userId;
    private String name;
    private String schoolClass;
    private String sex;
    private String grade;
    private String specialty;
    private String college;
    private String specialtyDirection;
    private String userName;
    private LoginUser loginUser;
}
