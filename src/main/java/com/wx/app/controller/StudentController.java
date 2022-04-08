package com.wx.app.controller;/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */

import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.entity.User;
import com.wx.app.service.UserService;
import com.wx.app.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(value = "学生Controller", tags = "管理学生账号")
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
    @DeleteMapping("/student")
    @PreAuthorize("hasAnyAuthority('admin')")
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
    @GetMapping("/student/list")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result studentList(PageDTO pageDTO, StudentInfoDTO studentTestInfo){
        log.info("pageDTO: {}",pageDTO.toString());
        log.info("studentTestInfo: {}",studentTestInfo.toString());

        return userService.getStudentList(pageDTO, studentTestInfo);
    }

    /**
     * 通过id查询学生信息
     * @param id
     * @return
     */
    @ApiOperation(value = "通过学生id获取学生信息")
    @GetMapping("/student/getById")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result studentInfo(Long id){
        log.info("userId: {}",id);

        return userService.getStudentInfo(id);
    }

    /**
     * 添加学生
     * @param user
     * @return
     */
    @ApiOperation(value = "添加学生")
    @PostMapping("/student")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result addStudent(@RequestBody User user){
        log.info("user: {}",user.toString());
        return userService.addStudent(user);
    }

    /**
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "修改学生")
    @PutMapping("/student")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result updateStudent(@RequestBody User user) {
        log.info("user: {}",user.toString());
        return userService.updateStudent(user);
    }

}
