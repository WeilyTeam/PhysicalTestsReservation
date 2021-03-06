package com.wx.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.entity.StudentFreeTest;
import com.wx.app.vo.StudentFreeTestVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Entity com.wx.app.entity.StudentFreeTest
*/
public interface StudentFreeTestMapper extends BaseMapper<StudentFreeTest> {

    Page<StudentFreeTestVo> selectFreeList(@Param("page") Page<StudentFreeTestVo> page,
                                           @Param("condition") StudentInfoDTO studentTestInfo,
                                           @Param("isPass") String isPass,
                                           @Param("semester") String semester);

    List<StudentFreeTest> hasMessage(@Param("userId") Long userId);
    //List<StudentFreeTestVo> selectFreeList(@Param("condition") StudentInfoDTO studentTestInfo);
}
