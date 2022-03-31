package com.wx.app.controller;/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */

import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.service.UserService;
import com.wx.app.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Api(value = "学生Controller", tags = "管理学生账号")
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Autowired
    private UserService userService;





    /**
     * 删除学生
     * @param userId
     * @return
     */
    @ApiOperation(value = "删除学生")
    @DeleteMapping("/deleteStudent")
    public Result deleteStudent(Long userId){
        log.info("userId: {}",userId);
        return userService.deleteStudent(userId);
    }

    /**
     * 学生list
     * @param pageDTO
     * @return
     */
    @ApiOperation(value = "获取学生列表")
    @GetMapping("/studentList")
    public Result studentList(PageDTO pageDTO, StudentInfoDTO studentTestInfo){
        log.info("pageDTO: {}",pageDTO.toString());
        log.info("studentTestInfo: {}",studentTestInfo.toString());

        Result studentList = userService.getStudentList(pageDTO, studentTestInfo);
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
        log.info("userId: {}",userId);

        Result studentList = userService.getStudentInfo(userId);
        return studentList;
    }

}
