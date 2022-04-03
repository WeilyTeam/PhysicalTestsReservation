package com.wx.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wx.app.vo.StudentInfoVo;
import com.wx.app.vo.TeacherInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;


/**
 * 用户表(User)实体类
 *
 * @author 三更
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;

    /**
    * 主键
    */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String userName;
    private String name;
    private String password;
    private String identity;
    private String sex;
    private String grade;
    private String specialty;
    private String college;
    private String phone;

    public User(StudentInfoVo item, String identity) {
        this.userName = item.getUserName();
        this.name = item.getName();
        this.identity = identity;
        this.sex = item.getSex();
        this.grade = item.getGrade();
        this.specialty = item.getSpecialty();
        this.college = item.getCollege();
    }


    public User(TeacherInfoVo item, String identity) {
        this.userName = item.getUserName();
        this.name = item.getName();
        this.identity = identity;
        this.sex = item.getSex();
        this.college = item.getCollege();
        this.phone = item.getPhone();
    }
}
