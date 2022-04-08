package com.wx.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.app.entity.StudentTestInfo;
import org.apache.ibatis.annotations.Param;

/**
* @Entity com.wx.app.entity.StudentTestInfo
*/
public interface StudentTestInfoMapper extends BaseMapper<StudentTestInfo> {


    void updateOrderNum(StudentTestInfo studentTestInfo);

    void decOrder(@Param("id") Long testId);

    StudentTestInfo getById(Long id);
}
