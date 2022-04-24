package com.wx.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.entity.StudentTest;
import com.wx.app.entity.User;
import com.wx.app.enums.CommonCode;
import com.wx.app.mapper.ShowStudentInfoVoMapper;
import com.wx.app.mapper.StudentTestMapper;
import com.wx.app.mapper.UserMapper;
import com.wx.app.service.StudentTestService;
import com.wx.app.utils.Result;
import com.wx.app.vo.ShowStudentInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class StudentTestServiceImpl extends ServiceImpl<StudentTestMapper, StudentTest>
        implements StudentTestService{

    @Autowired
    private StudentTestMapper studentTestMapper;

    @Autowired
    private ShowStudentInfoVoMapper showStudentInfoVoMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void createOrder(StudentTest studentTest) {
        studentTestMapper.insert(studentTest);
    }

    @Override
    public StudentTest checkOrder(Long userId, String semester) {
        QueryWrapper<StudentTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("semester", semester);
        return studentTestMapper.selectOne(queryWrapper);
    }

    @Override
    public Result getTestList(PageDTO pageDTO, Long id, StudentInfoDTO studentTestInfo) {
        //Page<ShowStudentInfoVo> page = new Page<>(pageDTO.getCurrent(),pageDTO.getSize());
        //Page<ShowStudentInfoVo> studentById = userMapper.getStudentById(page, id, studentTestInfo);
        Page<StudentTest> page = new Page<>(pageDTO.getCurrent(),pageDTO.getSize());
        QueryWrapper<StudentTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("test_id", id);
        //if (studentTestInfo.getSpecialtyClass() != null){
        //    queryWrapper.like("specialty_class", studentTestInfo.getSpecialtyClass());
        //}
        Page<StudentTest> studentTestPage = studentTestMapper.selectPage(page, queryWrapper);
        Page<Map<String,Object>> res = new Page<>();
        res.setSize(studentTestPage.getSize());
        res.setCurrent(studentTestPage.getCurrent());
        res.setTotal(studentTestPage.getTotal());
        res.setPages(studentTestPage.getPages());
        List<Map<String,Object>> list = new ArrayList<>();
        for (StudentTest studentTest : studentTestPage.getRecords()) {
            User user = userMapper.selectById(studentTest.getUserId());
            if (user != null) {
                ShowStudentInfoVo showStudentInfoVo = new ShowStudentInfoVo(user);
                String specialtyClass = user.getSpecialtyClass();
                Map<String,Object> map = new HashMap<>();
                map.put("id", studentTest.getId().toString());
                map.put("user", showStudentInfoVo);
                map.put("semester", studentTest.getSemester());
                map.put("specialtyClass",specialtyClass);
                list.add(map);
            }
        }
        res.setRecords(list);
        return new Result(CommonCode.SUCCESS,res);
    }
}
