package com.wx.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.app.entity.User;
import com.wx.app.vo.StudentInfoVo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {
    Page<StudentInfoVo> getStudentList (@Param("page") Page<StudentInfoVo> page);

    StudentInfoVo getStudentInfo(Long userId);
}
