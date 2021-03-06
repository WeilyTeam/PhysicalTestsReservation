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
import com.wx.app.dto.TeacherDTO;
import com.wx.app.entity.LoginUser;
import com.wx.app.entity.User;
import com.wx.app.enums.CommonCode;
import com.wx.app.mapper.ShowStudentInfoVoMapper;
import com.wx.app.mapper.StudentMapper;
import com.wx.app.mapper.TeacherInfoMapper;
import com.wx.app.mapper.UserMapper;
import com.wx.app.service.UserService;
import com.wx.app.utils.RedisCache;
import com.wx.app.utils.Result;
import com.wx.app.utils.UserUtils;
import com.wx.app.vo.ShowStudentInfoVo;
import com.wx.app.vo.StudentInfoVo;
import com.wx.app.vo.TeacherInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherInfoMapper teacherInfoMapper;
    @Autowired
    private ShowStudentInfoVoMapper showStudentInfoVoMapper;
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
        Page<StudentInfoVo> page = new Page<>(pageDTO.getCurrent(),pageDTO.getSize());
        //查询学生信息
        QueryWrapper<StudentInfoVo> queryWrapper = new QueryWrapper<>();

        if (studentTestInfo.getName() != null) {
            queryWrapper.like("name", studentTestInfo.getName());
        }else if (studentTestInfo.getGrade() != null){
            queryWrapper.like("grade", studentTestInfo.getGrade());
        }else if (studentTestInfo.getUserName() != null){
            queryWrapper.like("user_name", studentTestInfo.getUserName());
        }
        queryWrapper.and(wrapper -> wrapper.eq("identity", "学生")
                .or().eq("identity", "班长")
        );
        Page<StudentInfoVo> userPage = studentMapper.selectPage(page, queryWrapper);
        return new Result(CommonCode.SUCCESS, userPage);
    }

    @Override
    public Result getStudentMonitorList(PageDTO pageDTO, StudentInfoDTO studentTestInfo) {
        Page<StudentInfoVo> page = new Page<>(pageDTO.getCurrent(),pageDTO.getSize());
        //查询学生信息
        QueryWrapper<StudentInfoVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identity","班长");
        if (studentTestInfo.getName() != null) {
            queryWrapper.like("name", studentTestInfo.getName());
        }else if (studentTestInfo.getGrade() != null){
            queryWrapper.like("grade", studentTestInfo.getGrade());
        }else if (studentTestInfo.getUserName() != null){
            queryWrapper.like("user_name", studentTestInfo.getUserName());
        }
        Page<StudentInfoVo> userPage = studentMapper.selectPage(page, queryWrapper);
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
    public Result getTeacherList(PageDTO pageDTO, TeacherDTO teacherDTO) {
        QueryWrapper<TeacherInfoVo> eq = new QueryWrapper<TeacherInfoVo>().eq("identity", "老师");
        if (teacherDTO.getCollege() != null) {
            eq.like("specialty_class", teacherDTO.getCollege());
        }else if (teacherDTO.getName() != null) {
            eq.like("name", teacherDTO.getName());
        }else if (teacherDTO.getUserName() != null) {
            eq.like("user_name", teacherDTO.getUserName());
        }
        if (pageDTO.getSize() == null || pageDTO.getCurrent() == null) {
            List<TeacherInfoVo> users = teacherInfoMapper.selectList(eq);
            return new Result(CommonCode.SUCCESS,users);
        }

        Page<TeacherInfoVo> page = new Page<>(pageDTO.getCurrent(),pageDTO.getSize());
        Page<TeacherInfoVo> userPage = teacherInfoMapper.selectPage(page, eq);
        return new Result(CommonCode.SUCCESS,userPage);
    }

    @Override
    public Result addTeacher(TeacherInfoVo teacherInfoVo) {
        User user = new User();
        user.setId(teacherInfoVo.getId());
        user.setUserName(teacherInfoVo.getUserName());
        user.setName(teacherInfoVo.getName());
        user.setSex(teacherInfoVo.getSex());
        user.setSpecialtyClass(teacherInfoVo.getSpecialtyClass());
        user.setPhone(teacherInfoVo.getPhone());
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
    public Result updateTeacher(TeacherInfoVo teacherInfoVo) {
        User user = new User();
        user.setId(teacherInfoVo.getId());
        user.setUserName(teacherInfoVo.getUserName());
        user.setName(teacherInfoVo.getName());
        user.setSex(teacherInfoVo.getSex());
        user.setSpecialtyClass(teacherInfoVo.getSpecialtyClass());
        user.setPhone(teacherInfoVo.getPhone());
        user.setIdentity("老师");
        redisCache.deleteObject("login:"+user.getId());
        int i = 0;
        if (user.getId() != null) {
            i = userMapper.updateById(user);
        }else {
            QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("user_name", user.getUserName());
            queryWrapper.eq("identity", "老师");
            i = userMapper.update(user,queryWrapper);
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

    @Override
    public Result getTeacherById(Long userId) {

        //通过id获取信息
        //从redis获取用户信息
        String redisKey = "login:" + userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (loginUser != null) {
            User redisUser = loginUser.getUser();
            redisUser.setPassword(null);
            return new Result(CommonCode.SUCCESS, redisUser);
        }
        //如果redis中没有，则查询
        User user = userMapper.selectById(userId);
        user.setPassword(null);
        return new Result(CommonCode.SUCCESS, user);
    }

    @Override
    public Result addStudent(User user) {
        PasswordEncoder ps = new BCryptPasswordEncoder();
        String encode = ps.encode(user.getUserName());
        user.setPassword(encode);
        user.setIdentity("学生");

        int insert = userMapper.insert(user);
        if (insert == 0){
            return new Result(CommonCode.FAILURE);
        }
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Result updateStudent(User user) {

        redisCache.deleteObject("login:"+user.getId());
        user.setIdentity("学生");
        int i = 0;
        if (user.getId() != null) {
            i = userMapper.updateById(user);
        }else {
            QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("user_name", user.getUserName());
            queryWrapper.eq("identity", "学生");
            i = userMapper.update(user,queryWrapper);
        }
        if (i == 0) {
            return new Result(CommonCode.FAILURE);
        }
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Result studentToMonitor(Long userId) {
        //获取原本loginuser
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        User user = userMapper.selectById(userId);
        user.setIdentity("班长");
        int i = userMapper.updateById(user);
        //如果原本loginuser存在就更新他
        if (loginUser != null) {
            loginUser.setUser(user);
            List<String> list = new ArrayList<String>();
            list.add("monitor");
            loginUser.setPermissions(list);
            redisCache.deleteObject("login:"+userId);
            redisCache.setCacheObject("login:"+userId, loginUser,24*31, TimeUnit.HOURS);
        }
        if (i==0){
            return new Result(CommonCode.FAILURE);
        }
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Result studentToMonitorBack(Long userId) {
        //获取原本loginuser
        LoginUser loginUser = redisCache.getCacheObject("login:" + userId);
        User user = userMapper.selectById(userId);
        user.setIdentity("学生");
        int i = userMapper.updateById(user);
        if (loginUser != null) {
            loginUser.setUser(user);
            List<String> list = new ArrayList<String>();
            list.add("student");
            loginUser.setPermissions(list);
            redisCache.deleteObject("login:"+userId);
            redisCache.setCacheObject("login:"+userId, loginUser,24*31, TimeUnit.HOURS);
        }
        if (i==0){
            return new Result(CommonCode.FAILURE);
        }
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Result getStudentsByClass(PageDTO pageDTO, String specialtyClass) {
        QueryWrapper<ShowStudentInfoVo> queryWrapper = new QueryWrapper<>();
        Page<ShowStudentInfoVo> page = new Page<>(pageDTO.getCurrent(),pageDTO.getSize());
        queryWrapper.eq("specialty_class", specialtyClass);
        Page<ShowStudentInfoVo> showStudentInfoVoPage = showStudentInfoVoMapper.selectPage(page, queryWrapper);
        return new Result(CommonCode.SUCCESS,showStudentInfoVoPage);
    }
}
