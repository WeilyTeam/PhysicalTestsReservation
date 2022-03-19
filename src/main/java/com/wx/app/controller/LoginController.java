package com.wx.app.controller;/**
 * @author lingqu
 * @date 2022/3/1
 * @apiNote
 */

import com.wx.app.entity.User;
import com.wx.app.service.LoginService;
import com.wx.app.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(value = "登录、注册、登出 controller", tags = {"用户登录、注册、登出"})
@RequestMapping("/user")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录接口
     * @param user
     * @return
     */
    @ApiOperation(value = "登录接口")
    @PostMapping("login")
    public Result login(@RequestBody User user){
        log.info("{}",user.toString());
        return loginService.login(user);
    }

    /**
     * 注册接口
     * @param user
     * @return
     */
    @ApiOperation(value = "注册接口")
    @PostMapping("register")
    public Result register(@RequestBody User user){
        return loginService.register(user);
    }

    /**
     * 登出接口
     * @return
     */
    @ApiOperation(value = "登出接口")
    @GetMapping("logout")
    public Result logout(){
        return loginService.logout();
    }

    /**
     * 登出接口
     * @return
     */
    @ApiOperation(value = "刷新token")
    @GetMapping("refreshToken")
    public Result refreshToken(){
        return loginService.refreshToken();
    }
}
