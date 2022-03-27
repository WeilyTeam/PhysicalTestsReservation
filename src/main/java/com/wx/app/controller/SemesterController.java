package com.wx.app.controller;

import com.wx.app.dto.StudetnSemesterDTO;
import com.wx.app.entity.StudetnSemester;
import com.wx.app.service.StudetnSemesterService;
import com.wx.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lingqu
 * @date 2022/3/27
 * @apiNote
 */
@RestController
@Slf4j
public class SemesterController {
    @Autowired
    private StudetnSemesterService studetnSemesterService;

    @GetMapping("/semester")
    public Result getAllSemester(){
        return studetnSemesterService.getAllSemester();
    }

    @PostMapping("/semester")
    public Result addSemester(@RequestBody StudetnSemester semester){
        log.info("semester: {}",semester.toString());
        return studetnSemesterService.addSemester(semester);
    }

    @DeleteMapping("/semester")
    public Result deleteSemester(String semester){
        log.info("semester: {}",semester);
        return studetnSemesterService.deleteSemester(semester);
    }

    @PutMapping("/semester")
    public Result updateSemester(@RequestBody StudetnSemesterDTO semester){
        log.info("semester: {}",semester.toString());
        return studetnSemesterService.updateSemester(semester);
    }
}
