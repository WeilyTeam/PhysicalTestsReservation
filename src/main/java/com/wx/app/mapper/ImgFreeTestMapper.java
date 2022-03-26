package com.wx.app.mapper;

import com.wx.app.entity.ImgFreeTest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.wx.app.entity.ImgFreeTest
 */
public interface ImgFreeTestMapper extends BaseMapper<ImgFreeTest> {

    void insertImages(@Param("images") List<String> images, @Param("testId") Long id);

    List<String> selectListById(@Param("id") Long id);
}




