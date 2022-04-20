package com.wx.app.controller;/**
 * @author lingqu
 * @date 2022/3/9
 * @apiNote
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.util.concurrent.RateLimiter;
import com.wx.app.dto.OrderDTO;
import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.dto.TestListCondition;
import com.wx.app.entity.StudentTestInfo;
import com.wx.app.entity.User;
import com.wx.app.enums.CommonCode;
import com.wx.app.service.OrderService;
import com.wx.app.utils.Result;
import com.wx.app.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Api(value = "预约Controller", tags = "预约相关")
@RequestMapping("/reserve")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    //创建令牌桶实例
    private RateLimiter rateLimiter= RateLimiter.create(30);

    /**
     * 获取体测列表
     * @param pageDTO
     * @return
     */
    @ApiOperation(value = "获取体测列表")
    @GetMapping("/testList")
    public Result testList(PageDTO pageDTO, TestListCondition testListCondition) {

        log.info("pageDTO: {}",pageDTO.toString());
        log.info("testListCondition: {}",testListCondition.toString());

        Page<StudentTestInfo> list = orderService.testList(pageDTO, testListCondition);
        return new Result(200,"操作成功",list);
    }


    /**
     * 获取体测列表
     * @param pageDTO
     * @return
     */
    @ApiOperation(value = "获取体测列表")
    @GetMapping("/allTestList")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public Result allTestList(PageDTO pageDTO, TestListCondition testListCondition) {
        log.info("pageDTO: {}",pageDTO.toString());
        log.info("testListCondition: {}",testListCondition.toString());

        Page<StudentTestInfo> list = orderService.allTestList(pageDTO, testListCondition);
        return new Result(200,"操作成功",list);
    }

    /**
     * 获取体测列表
     * @param
     * @return
     */
    @ApiOperation(value = "修改体测信息")
    @PutMapping("/updateTest")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public Result updateTest(@RequestBody StudentTestInfo studentTestInfo) {
        log.info("studentTestInfo: {}",studentTestInfo.toString());
        return orderService.updateTest(studentTestInfo);
    }

    /**
     * 根据token获取体测信息
     * @param
     * @return
     */
    @ApiOperation(value = "根据token获取体测信息")
    @GetMapping("/orderInfo")
    public Result getOrderInfo() {
        return orderService.getOrderInfo();
    }

    /**
     * 添加体测信息
     * @param studentTestInfo
     * @return
     */
    @ApiOperation(value = "添加体测信息")
    @PostMapping("/addTest")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public Result addTest(@RequestBody StudentTestInfo studentTestInfo) {
        log.info("studentTestInfo: {}",studentTestInfo.toString());
        return orderService.addTest(studentTestInfo);
    }

    /**
     * 删除体测信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除体测信息")
    @DeleteMapping("/deleteTestById")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public Result deleteTest(Long id) {
        log.info("id: {}",id);
        return orderService.deleteTestById(id);
    }

    /**
     * 获取学生预约列表
     * @param pageDTO
     * @return
     */
    @ApiOperation(value = "获取已预约列表")
    @GetMapping("/orderList")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public Result orderList(PageDTO pageDTO, Long id, StudentInfoDTO studentTestInfo) {
        log.info("pageDTO: {}",pageDTO.toString());
        log.info("id: {}",id);
        log.info("studentTestInfo: {}",studentTestInfo);
        return orderService.orderList(pageDTO, id, studentTestInfo);
    }



    /**
     * 预约接口
     * @param orderDTO
     * @return
     */
    @ApiOperation(value = "预约接口")
    @PostMapping("/order")
    @PreAuthorize("hasAnyAuthority('monitor')")
    public Result order(@RequestBody OrderDTO orderDTO) {
        log.info("orderDTO: {}",orderDTO.toString());

        if (!rateLimiter.tryAcquire(3, TimeUnit.SECONDS)){
            log.info("当前请求被限流，直接抛弃，无法调用后序逻辑");
            return new Result(CommonCode.FAILURE_TO_ORDER);
        }
        User user = UserUtils.getUser();
        orderDTO.setSpecialtyClass(user.getSpecialtyClass());

        return orderService.orderByid(orderDTO);
    }


    /**
     * 预约接口
     * @param orderDTO
     * @return
     */
    @ApiOperation(value = "预约接口")
    @PostMapping("/orderByAdmin")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public Result orderByAdmin(@RequestBody OrderDTO orderDTO) {
        if (orderDTO.getSpecialtyClass() == null || orderDTO.getTestId() == null) {
            return  new Result(CommonCode.VALIDATE_FAILED);
        }
        log.info("orderDTO: {}",orderDTO.toString());

        if (!rateLimiter.tryAcquire(3, TimeUnit.SECONDS)){
            log.info("当前请求被限流，直接抛弃，无法调用后序逻辑");
            return new Result(CommonCode.FAILURE_TO_ORDER);
        }
        return orderService.orderByid(orderDTO);
    }

    /**
     * 删除体测信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除已预约信息")
    @DeleteMapping("/deleteOrderById")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public Result deleteOrderById(Long id) {
        log.info("id: {}",id);
        return orderService.deleteOrderById(id);
    }
}
