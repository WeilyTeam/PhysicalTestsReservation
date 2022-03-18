package com.wx.app.service.impl;/**
 * @author lingqu
 * @date 2022/3/9
 * @apiNote
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.app.dto.OrderDTO;
import com.wx.app.dto.PageDTO;
import com.wx.app.entity.StudentTest;
import com.wx.app.entity.StudentTestInfo;
import com.wx.app.enums.CommonCode;
import com.wx.app.service.OrderService;
import com.wx.app.service.StudentTestInfoService;
import com.wx.app.service.StudentTestService;
import com.wx.app.utils.Result;
import com.wx.app.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StudentTestInfoService studentTestInfoService;

    @Autowired
    private StudentTestService studentTestService;
    @Override
    public void orderByid(OrderDTO orderDTO) {
        //校验库存
        StudentTestInfo studentTestInfo = checkStock(orderDTO.getTestId());
        //扣除库存
        updateSale(studentTestInfo);
        //创建订单
        Long order = createOrder(studentTestInfo);
    }

    @Override
    public Page<StudentTestInfo> testList(PageDTO pageDTO) {
        Page<StudentTestInfo> studentTestInfo = studentTestInfoService.getTestList(pageDTO);
        return studentTestInfo;
    }

    @Override
    public Page<StudentTest> orderList(PageDTO pageDTO, Long id) {
        Page<StudentTest> studentTest = studentTestService.getTestList(pageDTO, id);
        return studentTest;
    }

    @Override
    public Result addTest(StudentTestInfo studentTestInfo) {
        boolean save = studentTestInfoService.save(studentTestInfo);
        if (save){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }

    @Override
    public Result deleteTestById(Long id) {
        boolean b = studentTestInfoService.removeById(id);
        if (b){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }

    @Override
    public Result deleteOrderById(Long id) {
        boolean b = studentTestService.removeById(id);
        if (b){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }

    //校验库存
    private StudentTestInfo checkStock(Long id){
        StudentTestInfo studentTestInfo = studentTestInfoService.checkStockById(id);
        if (studentTestInfo.getStore().equals(studentTestInfo.getOrderNum())){
            throw new RuntimeException("人数已满!!!");
        }
        Long userId = UserUtils.getUserId();
        StudentTest studentTest =  studentTestService.checkOrder(userId, studentTestInfo.getSemester());
        if (studentTest != null){
            throw new RuntimeException("本学期已预约");
        }
        return studentTestInfo;
    }

    //扣除库存
    private void updateSale(StudentTestInfo studentTestInfo){
        studentTestInfo.setOrderNum(studentTestInfo.getOrderNum()+1);
        studentTestInfoService.updateOrderNum(studentTestInfo);
    }

    //创建订单
    private Long createOrder(StudentTestInfo studentTestInfo){
        StudentTest studentTest=new StudentTest();

        Long userId = UserUtils.getUserId();

        studentTest.setUserId(userId)
                .setTestId(studentTestInfo.getId())
                .setSemester(studentTestInfo.getSemester());

        studentTestService.createOrder(studentTest);

        return studentTest.getId();
    }



}
