package com.wx.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.entity.StudentTest;
import com.wx.app.enums.CommonCode;
import com.wx.app.mapper.StudentTestMapper;
import com.wx.app.mapper.UserMapper;
import com.wx.app.service.StudentTestService;
import com.wx.app.utils.Result;
import com.wx.app.vo.ShowStudentInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class StudentTestServiceImpl extends ServiceImpl<StudentTestMapper, StudentTest>
        implements StudentTestService{

    @Autowired
    private StudentTestMapper studentTestMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createOrder(StudentTest studentTest) {
        studentTestMapper.insert(studentTest);
    }

    @Override
    public StudentTest checkOrder(String specialtyClass, String semester) {
        QueryWrapper<StudentTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("specialty_class", specialtyClass)
                .eq("semester", semester);
        return studentTestMapper.selectOne(queryWrapper);
    }

    @Override
    public Result getTestList(PageDTO pageDTO, Long id, StudentInfoDTO studentTestInfo) {
        Page<ShowStudentInfoVo> page = new Page<>(pageDTO.getCurrent(),pageDTO.getSize());
        Page<ShowStudentInfoVo> studentById = userMapper.getStudentById(page, id, studentTestInfo);
        return new Result(CommonCode.SUCCESS,studentById);
    }
}
