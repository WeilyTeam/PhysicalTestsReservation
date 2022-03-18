package com.wx.app.controller;/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */

import com.wx.app.dto.PageDTO;
import com.wx.app.service.UserService;
import com.wx.app.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(value = "学生Controller", tags = "管理学生账号")
@RequestMapping("/student")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 重置学生密码
     * @param userId
     * @return
     */
    @ApiOperation(value = "重置学生密码")
    @PutMapping("/resetStuPwd")
    public Result resetStuPwd(Long userId){
        return userService.resetStuPwd(userId);
    }

    /**
     * 逻辑删除学生
     * @param userId
     * @return
     */
    @ApiOperation(value = "删除学生")
    @DeleteMapping("/deleteStudent")
    public Result deleteStudent(Long userId){
        return userService.deleteStudent(userId);
    }

    /**
     * 学生list
     * @param pageDTO
     * @return
     */
    @ApiOperation(value = "获取学生列表")
    @GetMapping("/studentList")
    public Result studentList(PageDTO pageDTO){
        Result studentList = userService.getStudentList(pageDTO);
        return studentList;
    }

    /**
     * 通过id查询学生信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "通过学生id获取学生信息")
    @GetMapping("/studentInfo")
    public Result studentInfo(Long userId){
        Result studentList = userService.getStudentInfo(userId);
        return studentList;
    }

    /**
     * 通过token查询学生信息
     * @param
     * @return
     */
    @ApiOperation(value = "通过学生token获取学生信息")
    @GetMapping("/stuInfo")
    public Result stuInfo(){
        return userService.getstuInfo();
    }
}