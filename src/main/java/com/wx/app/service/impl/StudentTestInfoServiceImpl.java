package com.wx.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.dto.PageDTO;
import com.wx.app.service.StudentTestInfoService;
import com.wx.app.mapper.StudentTestInfoMapper;
import com.wx.app.entity.StudentTestInfo;
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
    @Override
    public Page<StudentTestInfo> getTestList(PageDTO pageDTO) {
        Page<StudentTestInfo> page = new Page<StudentTestInfo>(pageDTO.getCurrent(),pageDTO.getSize());
        QueryWrapper<StudentTestInfo> queryWrapper = new QueryWrapper<>();

        Page<StudentTestInfo> studentTestInfos = studentTestInfoMapper.selectPage(page,null);

        return studentTestInfos;
    }

    @Override
    public StudentTestInfo checkStockById(Long id) {

        StudentTestInfo studentTestInfo = studentTestInfoMapper.selectById(id);
        return studentTestInfo;
    }

    @Override
    public void updateOrderNum(StudentTestInfo studentTestInfo) {
        studentTestInfoMapper.updateOrderNum(studentTestInfo);
    }
}
