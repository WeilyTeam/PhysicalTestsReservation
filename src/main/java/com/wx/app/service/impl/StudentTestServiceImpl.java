package com.wx.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.dto.PageDTO;
import com.wx.app.entity.StudentTest;
import com.wx.app.service.StudentTestService;
import com.wx.app.mapper.StudentTestMapper;
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

    @Override
    public void createOrder(StudentTest studentTest) {
        studentTestMapper.insert(studentTest);
    }

    @Override
    public StudentTest checkOrder(Long id, String semester) {
        QueryWrapper<StudentTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id)
                .eq("semester", semester);
        return studentTestMapper.selectOne(queryWrapper);
    }

    @Override
    public Page<StudentTest> getTestList(PageDTO pageDTO, Long id) {
        Page<StudentTest> page = new Page<StudentTest>(pageDTO.getCurrent(),pageDTO.getSize());
        QueryWrapper<StudentTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("test_id", id);
        Page<StudentTest> studentTests = studentTestMapper.selectPage(page,queryWrapper);
        return studentTests;
    }
}
