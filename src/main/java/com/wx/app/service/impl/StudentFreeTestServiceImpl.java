package com.wx.app.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StudentFreeTestDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.entity.StudentFreeTest;
import com.wx.app.enums.CommonCode;
import com.wx.app.mapper.ImgFreeTestMapper;
import com.wx.app.mapper.StudentFreeTestMapper;
import com.wx.app.service.StudentFreeTestService;
import com.wx.app.utils.Result;
import com.wx.app.utils.UserUtils;
import com.wx.app.vo.StudentFreeTestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
*
*/
@Service
@Slf4j
@Transactional
public class StudentFreeTestServiceImpl extends ServiceImpl<StudentFreeTestMapper, StudentFreeTest>
implements StudentFreeTestService{
    @Autowired
    private StudentFreeTestMapper studentFreeTestMapper;

    @Autowired
    private ImgFreeTestMapper imgFreeTestMapper;

    @Override
    public Result freeTestList(PageDTO pageDTO, StudentInfoDTO studentTestInfo) {
        Page<StudentFreeTestVo> page = new Page<StudentFreeTestVo>(pageDTO.getCurrent(),pageDTO.getSize());
        Page<StudentFreeTestVo> studentFreeTestPage = studentFreeTestMapper.selectFreeList(page, studentTestInfo);
        List<StudentFreeTestVo> newStudentFreeTestVo = new ArrayList<>();
        for (StudentFreeTestVo studentFreeTestVo:studentFreeTestPage.getRecords()){
            List<String> imgFreeTests = imgFreeTestMapper.selectListById(studentFreeTestVo.getId());
            studentFreeTestVo.setImages(imgFreeTests);
            newStudentFreeTestVo.add(studentFreeTestVo);
        }
        studentFreeTestPage.setRecords(newStudentFreeTestVo);
        return new Result(CommonCode.SUCCESS,studentFreeTestPage);
    }

    @Override
    public Result freeTestApplication(StudentFreeTestDTO studentFreeTestDTO) {
        Long userId = UserUtils.getUserId();
        StudentFreeTest studentFreeTest = new StudentFreeTest(studentFreeTestDTO,userId);

        int insert = studentFreeTestMapper.insert(studentFreeTest);
        log.info("studentFreeTest的id {}",studentFreeTest.getId());
        if (insert == 0){
            return new Result(CommonCode.FAILURE);
        }
        imgFreeTestMapper.insertImages(studentFreeTestDTO.getImages(),studentFreeTest.getId());
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Result agreeApplication(Long id) {
        StudentFreeTest studentFreeTest = studentFreeTestMapper.selectById(id);
        studentFreeTest.setIsPass("1");
        int i = studentFreeTestMapper.updateById(studentFreeTest);
        if (i > 0){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }

    @Override
    public Result deleteFreeTest(Long id) {
        int i = studentFreeTestMapper.deleteById(id);
        if (i > 0){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }
}
