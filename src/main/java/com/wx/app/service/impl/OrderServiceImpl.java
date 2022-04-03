package com.wx.app.service.impl;/**
 * @author lingqu
 * @date 2022/3/9
 * @apiNote
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.app.dto.OrderDTO;
import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.dto.TestListCondition;
import com.wx.app.entity.StudentTest;
import com.wx.app.entity.StudentTestInfo;
import com.wx.app.entity.User;
import com.wx.app.enums.CommonCode;
import com.wx.app.mapper.StudentTestInfoMapper;
import com.wx.app.mapper.StudentTestMapper;
import com.wx.app.mapper.UserMapper;
import com.wx.app.service.OrderService;
import com.wx.app.service.StudentTestInfoService;
import com.wx.app.service.StudentTestService;
import com.wx.app.utils.Result;
import com.wx.app.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StudentTestInfoService studentTestInfoService;

    @Autowired
    private StudentTestInfoMapper StudentTestInfoMapper;

    @Autowired
    private StudentTestService studentTestService;

    @Autowired
    private StudentTestMapper studentTestMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result orderByid(OrderDTO orderDTO) {
        //校验库存
        StudentTestInfo studentTestInfo = null;
        try {
            studentTestInfo = checkStock(orderDTO.getTestId());
        } catch (RuntimeException e) {
            String message = e.getMessage();
            return new Result(500,message);
        }
        //扣除库存
        updateSale(studentTestInfo);
        //创建订单
        Long order = createOrder(studentTestInfo);
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Page<StudentTestInfo> testList(PageDTO pageDTO, TestListCondition testListCondition) {

        Page<StudentTestInfo> studentTestInfo = studentTestInfoService.getTestList(pageDTO, testListCondition);
        return studentTestInfo;
    }

    @Override
    public Result orderList(PageDTO pageDTO, Long id, StudentInfoDTO studentTestInfo) {
        Result studentTest = studentTestService.getTestList(pageDTO, id, studentTestInfo);

        return studentTest;
    }

    @Override
    public Result addTest(StudentTestInfo studentTestInfo) {
        Long userId = UserUtils.getUserId();
        studentTestInfo.setHeadid(userId);
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
        //体测信息预约减一
        StudentTest byId = studentTestService.getById(id);
        studentTestInfoService.decOrder(byId.getTestId());
        //删除已预约的学生
        boolean b = studentTestService.removeById(id);
        if (b){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }

    @Override
    public Result getOrderInfo() {
        Long userId = UserUtils.getUserId();
        QueryWrapper<StudentTest> queryWrapper = new QueryWrapper<StudentTest>();
        queryWrapper.eq("user_id", userId);

        List<StudentTest> studentTests = studentTestMapper.selectList(queryWrapper);
        List<StudentTestInfo> studentTestInfo = new ArrayList<StudentTestInfo>();
        for(StudentTest studentTest:studentTests){
            StudentTestInfo studentTestInfo1 = StudentTestInfoMapper.selectById(studentTest.getTestId());
            User user = userMapper.selectById(studentTestInfo1.getHeadid());
            studentTestInfo1.setTeacherInfo(user);
            studentTestInfo.add(studentTestInfo1);
        }
        return new Result(CommonCode.SUCCESS,studentTestInfo);
    }

    @Override
    public Result updateTest(StudentTestInfo studentTestInfo) {
        int i = StudentTestInfoMapper.updateById(studentTestInfo);
        log.info("updateById  {}",i);
        if (i > 0){
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
