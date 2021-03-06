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
import com.wx.app.mapper.TeacherInfoMapper;
import com.wx.app.mapper.UserMapper;
import com.wx.app.service.OrderService;
import com.wx.app.service.StudentTestInfoService;
import com.wx.app.service.StudentTestService;
import com.wx.app.utils.Result;
import com.wx.app.utils.UserUtils;
import com.wx.app.vo.TeacherInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StudentTestInfoService studentTestInfoService;

    @Autowired
    private StudentTestInfoMapper studentTestInfoMapper;

    @Autowired
    private TeacherInfoMapper teacherInfoMapper;


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
        } catch (RuntimeException | ParseException e) {
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
    public Page<StudentTestInfo> allTestList(PageDTO pageDTO, TestListCondition testListCondition) {
        Page<StudentTestInfo> studentTestInfo = studentTestInfoService.getAllTestList(pageDTO, testListCondition);
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
        User user1 = UserUtils.getUser();
        QueryWrapper<StudentTest> queryWrapper = new QueryWrapper<StudentTest>();
        queryWrapper.eq("specialty_class", user1.getSpecialtyClass());

        List<StudentTest> studentTests = studentTestMapper.selectList(queryWrapper);
        List<StudentTestInfo> studentTestInfo = new ArrayList<StudentTestInfo>();
        for(StudentTest studentTest:studentTests){
            StudentTestInfo studentTestInfo1 = studentTestInfoMapper.selectById(studentTest.getTestId());
            TeacherInfoVo teacherInfoVo = teacherInfoMapper.selectById(studentTestInfo1.getHeadid());
            studentTestInfo1.setTeacherInfo(teacherInfoVo);
            studentTestInfo.add(studentTestInfo1);
        }
        return new Result(CommonCode.SUCCESS,studentTestInfo);
    }

    @Override
    public Result updateTest(StudentTestInfo studentTestInfo) {
        int i = studentTestInfoMapper.updateById(studentTestInfo);
        log.info("updateById  {}",i);
        if (i > 0){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }

    //校验库存
    private StudentTestInfo checkStock(Long id) throws ParseException {
        StudentTestInfo studentTestInfo = studentTestInfoService.checkStockById(id);
        String day = studentTestInfo.getDay();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = simpleDateFormat.parse(day);
        int d = parse.compareTo(new Date());
        if (d <= 0){
            throw new RuntimeException("预约时间已过");
        }
        if (studentTestInfo.getStore().equals(studentTestInfo.getOrderNum())){
            throw new RuntimeException("人数已满!!!");
        }
        User user = UserUtils.getUser();
        StudentTest studentTest =  studentTestService.checkOrder(user.getId(), studentTestInfo.getSemester(),user.getSpecialtyClass());
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

        User user = UserUtils.getUser();

        studentTest.setSpecialtyClass(user.getSpecialtyClass())
                .setTestId(studentTestInfo.getId())
                .setUserId(user.getId())
                .setSemester(studentTestInfo.getSemester());

        studentTestService.createOrder(studentTest);

        return studentTest.getId();
    }



}
