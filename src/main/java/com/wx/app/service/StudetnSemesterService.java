package com.wx.app.service;

import com.wx.app.dto.StudetnSemesterDTO;
import com.wx.app.entity.StudetnSemester;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.utils.Result;

/**
 *
 */
public interface StudetnSemesterService extends IService<StudetnSemester> {

    Result getAllSemester();

    Result addSemester(StudetnSemester semester);

    Result deleteSemester(String semester);

    Result updateSemester(StudetnSemesterDTO semester);
}
