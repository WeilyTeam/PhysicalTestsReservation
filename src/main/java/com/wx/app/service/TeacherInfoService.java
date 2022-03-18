package com.wx.app.service;

import com.wx.app.dto.PageDTO;
import com.wx.app.entity.TeacherInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.utils.Result;

/**
 *
 */
public interface TeacherInfoService extends IService<TeacherInfo> {

    Result getTeacherList(PageDTO pageDTO);
}
