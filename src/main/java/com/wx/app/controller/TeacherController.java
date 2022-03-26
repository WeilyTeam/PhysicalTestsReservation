package com.wx.app.controller;

import com.wx.app.dto.PageDTO;
import com.wx.app.entity.TeacherInfo;
import com.wx.app.service.TeacherInfoService;
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
@RequestMapping("/teacher")
@Slf4j
public class TeacherController {

    @Autowired
    private TeacherInfoService teacherInfoService;

    /**
     * 老师列表
     * @param pageDTO
     * @return
     */
    @ApiOperation(value = "负责老师列表")
    @GetMapping("/list")
    public Result teacherList(PageDTO pageDTO) {
        log.info("pageDTO: {}",pageDTO.toString());

        return teacherInfoService.getTeacherList(pageDTO);
    }

    /**
     * 老师列表
     * @param
     * @return
     */
    @ApiOperation(value = "负责老师列表")
    @PostMapping("/add")
    public Result teacherList(@RequestBody TeacherInfo teacherInfo) {
        log.info("teacherInfo: {}",teacherInfo.toString());
        return teacherInfoService.addTeacher(teacherInfo);
    }
}
