package com.wx.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.app.entity.StudentTestInfo;

/**
* @Entity com.wx.app.entity.StudentTestInfo
*/
public interface StudentTestInfoMapper extends BaseMapper<StudentTestInfo> {


    void updateOrderNum(StudentTestInfo studentTestInfo);
}
