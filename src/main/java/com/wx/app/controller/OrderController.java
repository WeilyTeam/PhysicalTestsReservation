package com.wx.app.controller;/**
 * @author lingqu
 * @date 2022/3/9
 * @apiNote
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.util.concurrent.RateLimiter;
import com.wx.app.dto.OrderDTO;
import com.wx.app.dto.PageDTO;
import com.wx.app.entity.StudentTest;
import com.wx.app.entity.StudentTestInfo;
import com.wx.app.enums.CommonCode;
import com.wx.app.service.OrderService;
import com.wx.app.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Api(value = "预约Controller", tags = "预约相关")
@RequestMapping("/reserve")
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
    public Result testList(PageDTO pageDTO) {
        Page<StudentTestInfo> list = orderService.testList(pageDTO);
        return new Result(200,"操作成功",list);
    }

    /**
     * 添加体测信息
     * @param studentTestInfo
     * @return
     */
    @ApiOperation(value = "添加体测信息")
    @PostMapping("/addTest")
    public Result addTest(@RequestBody StudentTestInfo studentTestInfo) {
        return orderService.addTest(studentTestInfo);
    }

    /**
     * 删除体测信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除体测信息")
    @DeleteMapping("/deleteTestById")
    public Result deleteTest(Long id) {
        return orderService.deleteTestById(id);
    }

    /**
     * 获取学生预约列表
     * @param pageDTO
     * @return
     */
    @ApiOperation(value = "获取已预约列表")
    @GetMapping("/orderList")
    public Result orderList(PageDTO pageDTO, Long id) {
        Page<StudentTest> list = orderService.orderList(pageDTO, id);
        return new Result(200,"操作成功",list);
    }

    /**
     * 预约接口
     * @param orderDTO
     * @return
     */
    @ApiOperation(value = "预约接口")
    @PostMapping("/order")
    public Result order(@RequestBody OrderDTO orderDTO) {
        if (!rateLimiter.tryAcquire(3, TimeUnit.SECONDS)){
            System.out.println("当前请求被限流，直接抛弃，无法调用后序逻辑");
            return new Result(500,"当前人数过多，请重试!");
        }
        orderService.orderByid(orderDTO);
        return new Result(200,"操作成功");
    }

    /**
     * 删除体测信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除已预约信息")
    @DeleteMapping("/deleteOrderById")
    public Result deleteOrderById(Long id) {
        return orderService.deleteOrderById(id);
    }
}
