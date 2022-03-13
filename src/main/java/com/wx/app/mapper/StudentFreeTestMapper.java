package com.wx.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.app.entity.StudentFreeTest;
import com.wx.app.vo.StudentFreeTestVo;

/**
* @Entity com.wx.app.entity.StudentFreeTest
*/
public interface StudentFreeTestMapper extends BaseMapper<StudentFreeTest> {

    Page<StudentFreeTestVo> selectFreeList(Page<StudentFreeTestVo> page);
}
