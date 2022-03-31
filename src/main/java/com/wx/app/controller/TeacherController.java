package com.wx.app.controller;

import com.wx.app.dto.PageDTO;
import com.wx.app.entity.User;
import com.wx.app.service.UserService;
import com.wx.app.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author lingqu
 * @date 2022/3/18
 * @apiNote
 */

@RestController
@Api(value = "负责老师Controller", tags = "负责老师")
@Slf4j
public class TeacherController {

    @Autowired
    private UserService userService;

    /**
     * 老师列表
     * @param pageDTO
     * @return
     */
    @ApiOperation(value = "负责老师列表")
    @GetMapping("/teacherList")
    public Result teacherList(PageDTO pageDTO) {
        log.info("pageDTO: {}",pageDTO.toString());
        return userService.getTeacherList(pageDTO);
    }

    /**
     * 老师列表
     * @param id
     * @return
     */
    @ApiOperation(value = "负责老师列表")
    @GetMapping("/teacherInfoById")
    public Result getTeacherById(Long id) {
        log.info("id: {}",id);
        return userService.getTeacherById(id);
    }

    /**
     * 老师列表
     * @param user
     * @return
     */
    @ApiOperation(value = "添加老师")
    @PostMapping("/teacher")
    public Result addTeacher(@RequestBody User user) {
        log.info("user: {}",user.toString());

        return userService.addTeacher(user);
    }

    /**
     * 老师列表
     * @param user
     * @return
     */
    @ApiOperation(value = "修改老师")
    @PutMapping("/teacher")
    public Result updateTeacher(@RequestBody User user) {
        log.info("user: {}",user.toString());

        return userService.updateTeacher(user);
    }

    /**
     * 老师
     * @param id
     * @param userName
     * @return
     */
    @ApiOperation(value = "删除老师")
    @DeleteMapping("/teacher")
    public Result deleteTeacher(Long id, String userName) {
        log.info("id: {}",id);
        log.info("userName: {}",userName);

        return userService.deleteTeacher(id, userName);
    }

}
