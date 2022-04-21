package com.wx.app.controller;

import com.wx.app.dto.StudetnSemesterDTO;
import com.wx.app.entity.StudetnSemester;
import com.wx.app.service.StudetnSemesterService;
import com.wx.app.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author lingqu
 * @date 2022/3/27
 * @apiNote
 */
@RestController
@Slf4j
@Api(value = "学期 controller", tags = {"学期管理"})
public class SemesterController {
    @Autowired
    private StudetnSemesterService studetnSemesterService;

    @GetMapping("/semester")
    @ApiOperation(value = "查询学期")
    @PreAuthorize("hasAnyAuthority('admin','teacher','student','monitor')")
    public Result getAllSemester(){
        return studetnSemesterService.getAllSemester();
    }

    @PostMapping("/semester")
    @ApiOperation(value = "添加学期")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public Result addSemester(@RequestBody StudetnSemester semester){
        log.info("semester: {}",semester.toString());
        return studetnSemesterService.addSemester(semester);
    }

    @DeleteMapping("/semester")
    @ApiOperation(value = "删除学期")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public Result deleteSemester(String semester){
        log.info("semester: {}",semester);
        return studetnSemesterService.deleteSemester(semester);
    }

    @PutMapping("/semester")
    @ApiOperation(value = "修改学期")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public Result updateSemester(@RequestBody StudetnSemesterDTO semester){
        log.info("semester: {}",semester.toString());
        return studetnSemesterService.updateSemester(semester);
    }
}
