package com.wx.app.service.impl;/**
 * @author lingqu
 * @date 2022/3/1
 * @apiNote
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wx.app.entity.LoginUser;
import com.wx.app.entity.User;
import com.wx.app.enums.CommonCode;
import com.wx.app.mapper.UserMapper;
import com.wx.app.service.LoginService;
import com.wx.app.enums.CommonCode;
import com.wx.app.utils.JwtUtil;
import com.wx.app.utils.RedisCache;
import com.wx.app.utils.Result;
import com.wx.app.utils.UserUtils;
import com.wx.app.vo.StudentInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result login(User user) {
        //使用Authentication authenticate认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        }catch (Exception e){
            return new Result(CommonCode.FAILURE_TO_LOGIN);
        }


        //如果登录成功 生成jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId,1000*60*60L);
        String refreshToken = JwtUtil.createJWT(userId,1000*60*60*24*5L);
        Map<String,String> map = new HashMap<String,String>();
        map.put("token", token);
        map.put("refreshToken", refreshToken);

        //把完整用户信息保存到redis
        StudentInfoVo studentInfo = userManager.getStudentInfo(Long.parseLong(userId));
        studentInfo.setLoginUser(loginUser);
        redisCache.setCacheObject("login:"+userId, studentInfo,12, TimeUnit.HOURS);

        return new Result(CommonCode.SUCCESS_LOGIN, map);
    }



    @Override
    public Result logout() {
        //从SecurityContextHolder获取id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        Long id = principal.getUser().getId();

        //把redis中的登录信息删除
        redisCache.deleteObject("login:" + id);
        return new Result(CommonCode.SUCCESS_LOGOUT);
    }

    @Override
    public Result register(User user) {

        //判断用户名是否已经存在了
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, user.getUserName());
        User hasUser = userManager.selectOne(queryWrapper);
        if (!Objects.isNull(hasUser)){
            return new Result(CommonCode.FAILED_REGISTRATION);
        }

        //加密密码
        PasswordEncoder ps = new BCryptPasswordEncoder();
        String passwordEncoder = ps.encode(user.getPassword());
        user.setPassword(passwordEncoder);

        //得到执行人的id(如果可以每个人注册则不需要这个，并且取消注册接口的拦截)
        Long userId = UserUtils.getUserId();
        user.setCreateBy(userId);
        userManager.insert(user);
        return new Result(CommonCode.SUCCESS_REGISTRATION);
    }

    @Override
    public Result refreshToken() {
        Long userId = UserUtils.getUserId();
        String token = JwtUtil.createJWT(userId.toString(),1000*60*60L);
        Map<String,String> map = new HashMap<String,String>();
        map.put("token", token);
        return new Result(CommonCode.SUCCESS,map);
    }

}
