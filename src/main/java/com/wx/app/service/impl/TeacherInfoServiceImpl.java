package com.wx.app.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.dto.PageDTO;
import com.wx.app.entity.TeacherInfo;
import com.wx.app.enums.CommonCode;
import com.wx.app.service.TeacherInfoService;
import com.wx.app.mapper.TeacherInfoMapper;
import com.wx.app.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TeacherInfoServiceImpl extends ServiceImpl<TeacherInfoMapper, TeacherInfo>
    implements TeacherInfoService{

    @Autowired
    private TeacherInfoMapper teacherInfoMapper;

    @Override
    public Result getTeacherList(PageDTO pageDTO) {
        Page<TeacherInfo> page = new Page<TeacherInfo>(pageDTO.getCurrent(),pageDTO.getSize());
        Page<TeacherInfo> TeacherInfoPage = teacherInfoMapper.selectPage(page, null);
        return new Result(CommonCode.SUCCESS,TeacherInfoPage);
    }
}




