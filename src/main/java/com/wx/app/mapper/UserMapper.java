package com.wx.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.entity.User;
import com.wx.app.vo.StudentInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    Page<StudentInfoVo> getStudentList(@Param("page") Page<StudentInfoVo> page, @Param("condition") StudentInfoDTO studentTestInfo);
    List<StudentInfoVo> getStudentList( @Param("condition") StudentInfoDTO studentTestInfo);


    StudentInfoVo getStudentInfo(Long userId);

    Page<StudentInfoVo> getStudentById(@Param("page") Page<StudentInfoVo> page, @Param("id") Long id, @Param("condition") StudentInfoDTO studentTestInfo);
    List<StudentInfoVo> getStudentById(@Param("id") Long id, @Param("condition") StudentInfoDTO studentTestInfo);
}
