package com.wx.app.service.impl;/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.app.dto.PageDTO;
import com.wx.app.entity.User;
import com.wx.app.enums.CommonCode;
import com.wx.app.mapper.UserMapper;
import com.wx.app.service.UserService;
import com.wx.app.utils.Result;
import com.wx.app.utils.UserUtils;
import com.wx.app.vo.StudentInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result resetStuPwd(Long id) {
        User user = userMapper.selectById(id);

        //加密密码
        PasswordEncoder ps = new BCryptPasswordEncoder();
        String passwordEncoder = ps.encode(user.getUserName());
        user.setPassword(passwordEncoder);

        userMapper.updateById(user);
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Result getStudentList(PageDTO pageDTO) {
        Page<StudentInfoVo> page = new Page<>(pageDTO.getCurrent(),pageDTO.getSize());

        Page<StudentInfoVo> studentList = userMapper.getStudentList(page);
        Result ok = new Result(CommonCode.SUCCESS, studentList);
        return ok;
    }

    @Override
    public Result getStudentInfo(Long userId) {
        StudentInfoVo studentList = userMapper.getStudentInfo(userId);
        Result ok = new Result(CommonCode.SUCCESS, studentList);
        return ok;
    }

    @Override
    public Result deleteStudent(Long userId) {
        User user = userMapper.selectById(userId);
        user.setDelFlag(1);
        user.setStatus("1");
        int i = userMapper.updateById(user);
        if (i != 0){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }

    @Override
    public Result getstuInfo() {
        Long userId = UserUtils.getUserId();
        StudentInfoVo studentList = userMapper.getStudentInfo(userId);
        Result ok = new Result(CommonCode.SUCCESS, studentList);
        return ok;
    }
}