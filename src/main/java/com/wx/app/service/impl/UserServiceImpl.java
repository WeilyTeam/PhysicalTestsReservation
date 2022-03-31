package com.wx.app.service.impl;/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StuPwdDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.entity.LoginUser;
import com.wx.app.entity.User;
import com.wx.app.enums.CommonCode;
import com.wx.app.mapper.UserMapper;
import com.wx.app.service.UserService;
import com.wx.app.utils.RedisCache;
import com.wx.app.utils.Result;
import com.wx.app.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisCache redisCache;

    @Override
    public Result resetStuPwd(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return new Result(CommonCode.FAILURE_UPDATE_PASSWORD);
        }
        //加密密码，密码未用户名(学号)
        PasswordEncoder ps = new BCryptPasswordEncoder();
        String passwordEncoder = ps.encode(user.getUserName());
        user.setPassword(passwordEncoder);

        userMapper.updateById(user);
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Result getStudentList(PageDTO pageDTO, StudentInfoDTO studentTestInfo) {
        Page<User> page = new Page<>(pageDTO.getCurrent(),pageDTO.getSize());
        //查询学生信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identity","学生");
        if (studentTestInfo.getName() != null) {
            queryWrapper.eq("name", studentTestInfo.getName());
        }
        Page<User> userPage = userMapper.selectPage(page, queryWrapper);
        return new Result(CommonCode.SUCCESS, userPage);
    }

    @Override
    public Result getStudentInfo(Long userId) {
        //通过学生id获取学生信息
        //从redis获取用户信息
        String redisKey = "login:" + userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (loginUser != null) {
            User redisUser = loginUser.getUser();
            redisUser.setPassword(null);
            return new Result(CommonCode.SUCCESS, redisUser);
        }
        User user = userMapper.selectById(userId);
        user.setPassword(null);
        return new Result(CommonCode.SUCCESS, user);
    }

    @Override
    public Result deleteStudent(Long userId) {
        //逻辑删除学生
        int i = userMapper.deleteById(userId);
        if (i != 0){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }

    @Override
    public Result updateStuPwd(StuPwdDTO stuPwdDTO) {
        Long userId = UserUtils.getUserId();
        User user = userMapper.selectById(userId);
        //新密码与旧密码比较
        PasswordEncoder ps = new BCryptPasswordEncoder();
        if (ps.matches(stuPwdDTO.getNewPassword(),
                user.getPassword())){
            return new Result(CommonCode.OLD_PASSWORD_EQULS_NEW);
        }
        //加密密码
        if (ps.matches(stuPwdDTO.getPrePassword(),
                user.getPassword())){
            String passwordEncoder = ps.encode(stuPwdDTO.getNewPassword());
            user.setPassword(passwordEncoder);
            userMapper.updateById(user);
            //刷新缓存
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            LoginUser principal = (LoginUser) authentication.getPrincipal();
            principal.getUser().setPassword(passwordEncoder);
            //存入redis
            redisCache.setCacheObject("login:"+userId, principal,24*31, TimeUnit.HOURS);
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE_TO_CHANGE_PASSWORD);
    }

    @Override
    public Result getTeacherList(PageDTO pageDTO) {
        Page<User> page = new Page<>(pageDTO.getCurrent(),pageDTO.getSize());
        QueryWrapper<User> eq = new QueryWrapper<User>().eq("identity", "老师");
        Page<User> userPage = userMapper.selectPage(page, eq);
        return new Result(CommonCode.SUCCESS,userPage);
    }

    @Override
    public Result addTeacher(User user) {
        PasswordEncoder ps = new BCryptPasswordEncoder();
        String encode = ps.encode(user.getUserName());
        user.setPassword(encode);
        user.setIdentity("老师");
        int insert = userMapper.insert(user);
        if (insert == 0){
            return new Result(CommonCode.FAILURE);
        }
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Result updateTeacher(User user) {
        int i = 0;
        if (user.getId() != null) {
            i = userMapper.updateById(user);
        }else {
            i = userMapper.update(user,new QueryWrapper<User>().eq("user_name",user.getUserName()));
        }
        if (i == 0) {
            return new Result(CommonCode.FAILURE);
        }
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Result deleteTeacher(Long id, String userName) {
        int i = 0;
        if (id != null) {
            i = userMapper.deleteById(id);
        }else if (userName != null) {
            i = userMapper.delete(new  QueryWrapper<User>().eq("user_name",userName));
        }
        if (i == 0) {
            return new Result(CommonCode.FAILURE);
        }
        return new Result(CommonCode.SUCCESS);
    }
}
