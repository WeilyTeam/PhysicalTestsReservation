package com.wx.app.controller;

import com.wx.app.dto.PageDTO;
import com.wx.app.entity.StudentFreeTest;
import com.wx.app.service.StudentFreeTestService;
import com.wx.app.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lingqu
 * @date 2022/3/13
 * @apiNote
 */

@RestController
@Api(value = "免测Controller", tags = "免测相关")
@RequestMapping("/freeTest")
@Slf4j
public class FreeTestController {

    @Autowired
    private StudentFreeTestService studentFreeTestService;

    @GetMapping("/list")
    @ApiOperation(value = "获取免测列表")
    public Result freeTestList(PageDTO pageDTO){
        return studentFreeTestService.freeTestList(pageDTO);
    }

    @PostMapping("/application")
    @ApiOperation(value = "免测申请")
    public Result freeTestApplication(@RequestBody StudentFreeTest studentFreeTest){
        return studentFreeTestService.freeTestApplication(studentFreeTest);
    }

    @PutMapping("/agreeApplication")
    @ApiOperation(value = "同意免测")
    public Result agreeApplication(Long id){
        log.info("id: {}",id);
        return studentFreeTestService.agreeApplication(id);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除免测资格")
    public Result deleteFreeTest(Long id){
        return studentFreeTestService.deleteFreeTest(id);
    }
}
