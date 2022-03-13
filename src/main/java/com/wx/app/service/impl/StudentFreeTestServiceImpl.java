package com.wx.app.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.dto.PageDTO;
import com.wx.app.entity.StudentFreeTest;
import com.wx.app.enums.CommonCode;
import com.wx.app.service.StudentFreeTestService;
import com.wx.app.mapper.StudentFreeTestMapper;
import com.wx.app.utils.Result;
import com.wx.app.vo.StudentFreeTestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
*
*/
@Service
public class StudentFreeTestServiceImpl extends ServiceImpl<StudentFreeTestMapper, StudentFreeTest>
implements StudentFreeTestService{
    @Autowired
    private StudentFreeTestMapper studentFreeTestMapper;

    @Override
    public Result freeTestList(PageDTO pageDTO) {
        Page<StudentFreeTestVo> page = new Page<StudentFreeTestVo>(pageDTO.getCurrent(),pageDTO.getSize());
        Page<StudentFreeTestVo> studentFreeTestPage = studentFreeTestMapper.selectFreeList(page);
        return new Result(CommonCode.SUCCESS,studentFreeTestPage);
    }

    @Override
    public Result freeTestApplication(StudentFreeTest studentFreeTest) {
        int insert = studentFreeTestMapper.insert(studentFreeTest);
        if (insert > 0){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }

    @Override
    public Result agreeApplication(Long id) {
        StudentFreeTest studentFreeTest = studentFreeTestMapper.selectById(id);
        studentFreeTest.setIsPass("1");
        int i = studentFreeTestMapper.updateById(studentFreeTest);
        if (i > 0){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }

    @Override
    public Result deleteFreeTest(Long id) {
        int i = studentFreeTestMapper.deleteById(id);
        if (i > 0){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }
}
