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

import java.util.List;

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
        Page<TeacherInfo> page;
        Page<TeacherInfo> TeacherInfoPage = null;
        if (pageDTO.getCurrent() == null || pageDTO.getSize() == null){
            page = new Page<TeacherInfo>();
        }else {
            page = new Page<TeacherInfo>(pageDTO.getCurrent(),pageDTO.getSize());
            TeacherInfoPage = teacherInfoMapper.selectPage(page, null);
            return new Result(CommonCode.SUCCESS,TeacherInfoPage);
        }
        List<TeacherInfo> teacherInfos = teacherInfoMapper.selectList(null);
        return new Result(CommonCode.SUCCESS,teacherInfos);
    }

    @Override
    public Result addTeacher(TeacherInfo teacherInfo) {
        int insert = teacherInfoMapper.insert(teacherInfo);
        if (insert > 0){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }
}




