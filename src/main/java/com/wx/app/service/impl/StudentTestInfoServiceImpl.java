package com.wx.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.dto.PageDTO;
import com.wx.app.dto.TestListCondition;
import com.wx.app.entity.StudentTestInfo;
import com.wx.app.entity.User;
import com.wx.app.mapper.StudentTestInfoMapper;
import com.wx.app.mapper.TeacherInfoMapper;
import com.wx.app.mapper.UserMapper;
import com.wx.app.service.StudentTestInfoService;
import com.wx.app.utils.UserUtils;
import com.wx.app.vo.TeacherInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 */
@Service
public class StudentTestInfoServiceImpl extends ServiceImpl<StudentTestInfoMapper, StudentTestInfo>
        implements StudentTestInfoService{
    @Autowired
    private StudentTestInfoMapper studentTestInfoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeacherInfoMapper teacherInfoMapper;
    @Override
    public Page<StudentTestInfo> getTestList(PageDTO pageDTO, TestListCondition testListCondition) {
        Page<StudentTestInfo> page = new Page<StudentTestInfo>(pageDTO.getCurrent(),pageDTO.getSize());

        //创建QueryWrapper作为查询条件
        QueryWrapper<StudentTestInfo> queryWrapper = new QueryWrapper<>();

        //获取明天的日期
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往后增加一天,整数  往后推,负数往前移动
        calendar.add(Calendar.DATE,1);
        date=calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(date);
        queryWrapper.ge("day", format);
        User user1 = UserUtils.getUser();
        if ("老师".equals(user1.getIdentity())){
            queryWrapper.eq("headId", user1.getId());
        }
        queryWrapper.eq("del_flag", 0);
        //queryWrapper.("", 0);
        if(testListCondition.getLocation() != null){
            queryWrapper.like("location", testListCondition.getLocation());
        }
        if(testListCondition.getName() != null){
            queryWrapper.like("name", testListCondition.getName());
        }
        if(testListCondition.getDay() != null){
            queryWrapper.like("day", testListCondition.getDay());
        }
        if(testListCondition.getHour() != null){
            queryWrapper.like("hour", testListCondition.getHour());
        }if(testListCondition.getSemester() != null){
            queryWrapper.like("semester", testListCondition.getSemester());
        }
        //查询StudentTestInfo列表信息
        Page<StudentTestInfo> studentTestInfos = studentTestInfoMapper.selectPage(page,queryWrapper);
        List<StudentTestInfo> records = studentTestInfos.getRecords();
        for (StudentTestInfo record:records){
            //遍历查询老师信息
            TeacherInfoVo teacherInfoVo = teacherInfoMapper.selectById(record.getHeadid());
            record.setIsFull(record.getOrderNum().equals(record.getStore()));
            record.setTeacherInfo(teacherInfoVo);
        }
        studentTestInfos.setRecords(records);
        return studentTestInfos;
    }

    @Override
    public Page<StudentTestInfo> getAllTestList(PageDTO pageDTO, TestListCondition testListCondition) {
        Page<StudentTestInfo> page = new Page<StudentTestInfo>(pageDTO.getCurrent(),pageDTO.getSize());

        //创建QueryWrapper作为查询条件
        QueryWrapper<StudentTestInfo> queryWrapper = new QueryWrapper<>();
        User user1 = UserUtils.getUser();
        if ("老师".equals(user1.getIdentity())){
            queryWrapper.eq("headId", user1.getId());
        }
        queryWrapper.eq("del_flag", 0);
        //queryWrapper.("", 0);
        if(testListCondition.getLocation() != null){
            queryWrapper.like("location", testListCondition.getLocation());
        }
        if(testListCondition.getName() != null){
            queryWrapper.like("name", testListCondition.getName());
        }
        if(testListCondition.getDay() != null){
            queryWrapper.like("day", testListCondition.getDay());
        }
        if(testListCondition.getHour() != null){
            queryWrapper.like("hour", testListCondition.getHour());
        }
        if(testListCondition.getSemester() != null){
            queryWrapper.like("semester", testListCondition.getSemester());
        }
        //查询StudentTestInfo列表信息
        Page<StudentTestInfo> studentTestInfos = studentTestInfoMapper.selectPage(page,queryWrapper);
        List<StudentTestInfo> records = studentTestInfos.getRecords();
        for (StudentTestInfo record:records){
            //遍历查询老师信息
            //User user = userMapper.selectById(record.getHeadid());
            TeacherInfoVo teacherInfoVo = teacherInfoMapper.selectById(record.getHeadid());

            record.setIsFull(record.getOrderNum().equals(record.getStore()));
            record.setTeacherInfo(teacherInfoVo);
        }
        studentTestInfos.setRecords(records);
        return studentTestInfos;
    }

    @Override
    public StudentTestInfo checkStockById(Long id) {
        //通过id获取StudentTestInfo
        return studentTestInfoMapper.selectById(id);
    }

    @Override
    public void updateOrderNum(StudentTestInfo studentTestInfo) {
        //减库存
        studentTestInfoMapper.updateOrderNum(studentTestInfo);
    }

    @Override
    public void decOrder(Long testId) {
        studentTestInfoMapper.decOrder(testId);
    }
}
