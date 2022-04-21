package com.wx.app.controller;/**
 * @author lingqu
 * @date 2022/3/1
 * @apiNote
 */

import com.wx.app.dto.StuPwdDTO;
import com.wx.app.dto.UserDTO;
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
     * @param userDTO
     * @return
     */
    @ApiOperation(value = "登录接口")
    @PostMapping("login")
    public Result login(@RequestBody UserDTO userDTO){
        log.info("userDTO: {}",userDTO.toString());
        return loginService.login(userDTO);
    }

    /**
     * 登录接口
     * @param userDTO
     * @return
     */
    @ApiOperation(value = "登录接口")
    @PostMapping("loginAdmin")
    public Result loginAdmin(@RequestBody UserDTO userDTO){
        log.info("userDTO: {}",userDTO.toString());
        return loginService.loginAdmin(userDTO);
    }

    @ApiOperation(value = "登录接口")
    @GetMapping("userInfo")
    public Result userInfo(){
        return loginService.userInfo();
    }

    /**
     * 注册接口
     * @param user
     * @return
     */
    @ApiOperation(value = "注册接口")
    @PostMapping("register")
    public Result register(@RequestBody User user){
        log.info("user: {}",user.toString());
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

    /**
     * 重置密码
     * @param userId
     * @return
     */
    @ApiOperation(value = "重置密码")
    @PutMapping("/resetPwd")
    public Result resetStuPwd(Long userId){
        log.info("userId: {}",userId);

        return loginService.resetStuPwd(userId);
    }

    /**
     * 修改密码
     * @param
     * @return
     */
    @ApiOperation(value = "修改密码")
    @PutMapping("/updatePwd")
    public Result updateStuPwd(@RequestBody StuPwdDTO stuPwdDTO){
        log.info("Pwd{}",stuPwdDTO.toString());
        return loginService.updateStuPwd(stuPwdDTO);
    }

}
