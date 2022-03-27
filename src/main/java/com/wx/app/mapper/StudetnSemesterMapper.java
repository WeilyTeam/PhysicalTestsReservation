package com.wx.app.mapper;

import com.wx.app.entity.StudetnSemester;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity com.wx.app.entity.StudetnSemester
 */
public interface StudetnSemesterMapper extends BaseMapper<StudetnSemester> {

    List<String> getList();

}




