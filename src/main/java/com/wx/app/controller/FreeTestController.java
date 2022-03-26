package com.wx.app.controller;

import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StudentFreeTestDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.service.ImageService;
import com.wx.app.service.StudentFreeTestService;
import com.wx.app.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private ImageService imageService;

    @GetMapping("/list")
    @ApiOperation(value = "获取免测列表")
    public Result freeTestList(PageDTO pageDTO, StudentInfoDTO studentTestInfo){
        log.info("pageDTO: {}",pageDTO.toString());
        log.info("studentTestInfo: {}",studentTestInfo.toString());
        return studentFreeTestService.freeTestList(pageDTO, studentTestInfo);
    }

    @PostMapping("/application")
    @ApiOperation(value = "免测申请")
    public Result freeTestApplication(@RequestBody StudentFreeTestDTO studentFreeTestDTO){
        log.info("studentFreeTest: {}",studentFreeTestDTO);
        return studentFreeTestService.freeTestApplication(studentFreeTestDTO);
    }

    /**
     * 上传文件到项目的静态文件目录
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping("/uploadImg")
    @ApiOperation(value = "图片上传")
    public Result uploadImage(@RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(file);
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
        log.info("id: {}",id);
        return studentFreeTestService.deleteFreeTest(id);
    }
}
