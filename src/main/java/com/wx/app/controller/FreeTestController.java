package com.wx.app.controller;

import com.wx.app.dto.*;
import com.wx.app.service.ImageService;
import com.wx.app.service.StudentFreeTestService;
import com.wx.app.utils.Result;
import com.wx.app.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('admin')")
    @ApiOperation(value = "获取免测列表")
    public Result freeTestList(PageDTO pageDTO, AuditDOT auditDOT, StudentInfoDTO studentTestInfo){
        log.info("pageDTO: {}",pageDTO.toString());
        log.info("auditDOT: {}",auditDOT.toString());
        log.info("studentTestInfo: {}",studentTestInfo.toString());
        return studentFreeTestService.freeTestList(pageDTO, studentTestInfo, auditDOT);
    }



    @GetMapping("/getById")
    @ApiOperation(value = "获取免测列表")
    public Result freeTestById(Long id){
        log.info("id: {}",id);
        return studentFreeTestService.freeTestById(id);
    }



    @GetMapping("/hasMessage")
    @ApiOperation(value = "是否有未读免测消息")
    @PreAuthorize("hasAnyAuthority('student','monitor')")
    public Result hasMessage(){
        return studentFreeTestService.hasMessage();
    }



    @GetMapping("/freeInfo")
    @ApiOperation(value = "根据token获取免测")
    @PreAuthorize("hasAnyAuthority('student','monitor')")
    public Result freeTestByToken(){
        Long userId = UserUtils.getUserId();
        return studentFreeTestService.freeTestByToken(userId);
    }

    @PreAuthorize("hasAnyAuthority('student','monitor')")
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
    @PreAuthorize("hasAnyAuthority('student','monitor')")
    @ApiOperation(value = "图片上传")
    public Result uploadImage(@RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(file);
    }


    @PutMapping("/agreeApplication")
    @ApiOperation(value = "同意免测")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result agreeApplication(@RequestBody FreeTestDTO freeTestDTO){
        log.info("freeTestDTO: {}",freeTestDTO.toString());
        return studentFreeTestService.agreeApplication(freeTestDTO);
    }


    @PutMapping("/rejectApplication")
    @ApiOperation(value = "拒绝免测")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result rejectApplication(@RequestBody FreeTestDTO freeTestDTO){
        log.info("freeTestDTO: {}",freeTestDTO.toString());
        return studentFreeTestService.rejectApplication(freeTestDTO);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除免测")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result deleteFreeTest(Long id){
        log.info("id: {}",id);
        return studentFreeTestService.deleteFreeTest(id);
    }
}
