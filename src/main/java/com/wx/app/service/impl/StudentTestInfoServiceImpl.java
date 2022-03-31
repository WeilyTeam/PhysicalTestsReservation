package com.wx.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.dto.PageDTO;
import com.wx.app.dto.TestListCondition;
import com.wx.app.entity.StudentTestInfo;
import com.wx.app.entity.User;
import com.wx.app.mapper.StudentTestInfoMapper;
import com.wx.app.mapper.UserMapper;
import com.wx.app.service.StudentTestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public Page<StudentTestInfo> getTestList(PageDTO pageDTO, TestListCondition testListCondition) {
        Page<StudentTestInfo> page = new Page<StudentTestInfo>(pageDTO.getCurrent(),pageDTO.getSize());

        //创建QueryWrapper作为查询条件
        QueryWrapper<StudentTestInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);
        //queryWrapper.("", 0);
        if(testListCondition.getLocation() != null){
            queryWrapper.like("location", testListCondition.getLocation());
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
            User user = userMapper.selectById(record.getHeadid());
            record.setIsFull(record.getOrderNum().equals(record.getStore()));
            record.setTeacherInfo(user);
        }
        studentTestInfos.setRecords(records);
        return studentTestInfos;
    }

    @Override
    public StudentTestInfo checkStockById(Long id) {
        //通过id获取StudentTestInfo
        StudentTestInfo studentTestInfo = studentTestInfoMapper.selectById(id);
        return studentTestInfo;
    }

    @Override
    public void updateOrderNum(StudentTestInfo studentTestInfo) {
        //减库存
        studentTestInfoMapper.updateOrderNum(studentTestInfo);
    }
}
