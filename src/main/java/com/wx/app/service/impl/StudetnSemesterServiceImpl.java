package com.wx.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.dto.StudetnSemesterDTO;
import com.wx.app.entity.StudetnSemester;
import com.wx.app.enums.CommonCode;
import com.wx.app.mapper.StudetnSemesterMapper;
import com.wx.app.service.StudetnSemesterService;
import com.wx.app.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class StudetnSemesterServiceImpl extends ServiceImpl<StudetnSemesterMapper, StudetnSemester>
    implements StudetnSemesterService{

    @Autowired
    private StudetnSemesterMapper studetnSemesterMapper;

    @Override
    public Result getAllSemester() {
        List<String> studetnSemesters = studetnSemesterMapper.getList();
        return new Result(CommonCode.SUCCESS,studetnSemesters);
    }

    @Override
    public Result addSemester(StudetnSemester semester) {
        QueryWrapper<StudetnSemester> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("semester_name",semester.getSemesterName());
        StudetnSemester studetnSemester = studetnSemesterMapper.selectOne(queryWrapper);
        if (studetnSemester != null){
            return new Result(CommonCode.FAILURE_SEMESTER_EXIST);
        }
        int insert = studetnSemesterMapper.insert(semester);
        if (insert == 0){
            return new Result(CommonCode.FAILURE);
        }
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Result deleteSemester(String semester) {
        QueryWrapper<StudetnSemester> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("semester_name",semester);
        int delete = studetnSemesterMapper.delete(queryWrapper);
        if (delete == 0){
            return new Result(CommonCode.FAILURE);
        }
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Result updateSemester(StudetnSemesterDTO semester) {
        int semesterName = studetnSemesterMapper.update(new StudetnSemester(semester.getNewSemesterName()),
                new QueryWrapper<StudetnSemester>().eq("semester_name", semester.getPreSemesterName()));
        if (semesterName == 0){
            return new Result(CommonCode.FAILURE);
        }
        return new Result(CommonCode.SUCCESS);
    }

}




