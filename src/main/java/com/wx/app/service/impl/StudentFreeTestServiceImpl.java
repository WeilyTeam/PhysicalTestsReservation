package com.wx.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.dto.*;
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

import java.text.SimpleDateFormat;
import java.util.*;

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
    public Result freeTestList(PageDTO pageDTO, StudentInfoDTO studentTestInfo, AuditDOT auditDOT) {
        String isPass = null;
        if(auditDOT.getIsPass() != null){
            if ("1".equals(auditDOT.getIsPass())){
                isPass = "1";
            }else if ("2".equals(auditDOT.getIsPass())){
                isPass = "2";
            }else if ("0".equals(auditDOT.getIsPass())){
                isPass = "0";
            }
        }


        String semester = auditDOT.getSemester();
        //分页插件，查询StudentFreeTestVo列表
        Page<StudentFreeTestVo> page = new Page<StudentFreeTestVo>(pageDTO.getCurrent(),pageDTO.getSize());
        Page<StudentFreeTestVo> studentFreeTestPage = studentFreeTestMapper.selectFreeList(page, studentTestInfo, isPass, semester);
        List<StudentFreeTestVo> newStudentFreeTestVo = new ArrayList<>();

        //查询图片路由
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
        QueryWrapper<StudentFreeTest> queryWrapper = new QueryWrapper<>();
        Long userId = UserUtils.getUserId();
        queryWrapper.eq("semester", studentFreeTestDTO.getSemester());
        queryWrapper.eq("user_id", userId);
        List<StudentFreeTest> studentFreeTests = studentFreeTestMapper.selectList(queryWrapper);

        if (studentFreeTests.size() != 0){
            return new Result(CommonCode.FAILURE_APPLICATION);
        }
        if (studentFreeTestDTO.getType() == null){
            studentFreeTestDTO.setType(0);
        }
        StudentFreeTest studentFreeTest = new StudentFreeTest(studentFreeTestDTO,userId);

        //插入免测申请数据
        int insert = studentFreeTestMapper.insert(studentFreeTest);
        log.info("studentFreeTest的id {}",studentFreeTest.getId());
        if (insert == 0){
            return new Result(CommonCode.FAILURE);
        }
        //插入免测申请的图片路由
        if(studentFreeTestDTO.getImages().size() != 0){
            imgFreeTestMapper.insertImages(studentFreeTestDTO.getImages(),studentFreeTest.getId());
        }
        return new Result(CommonCode.SUCCESS);
    }

    @Override
    public Result agreeApplication(FreeTestDTO freeTestDTO) {
        //通过查询id查询studentFreeTest信息
        StudentFreeTest studentFreeTest = studentFreeTestMapper.selectById(freeTestDTO.getId());
        //设置为通过，并更新数据库
        studentFreeTest.setIsPass("1");
        studentFreeTest.setAuditMessage(freeTestDTO.getAuditMessage());
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        studentFreeTest.setAuditTime(sdf.format(data));
        studentFreeTest.setAuditMessage(freeTestDTO.getAuditMessage());
        //未读
        studentFreeTest.setIsRead(1);
        studentFreeTest.setHandler(UserUtils.getUser().getName());
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

    @Override
    public Result freeTestByToken(Long userId) {
        QueryWrapper<StudentFreeTest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<StudentFreeTest> studentFreeTests = studentFreeTestMapper.selectList(queryWrapper);
        List<Map<String,Object>> res = new ArrayList<>();
        for(StudentFreeTest studentFreeTest : studentFreeTests){
            Map<String,Object> data = new HashMap<>();
            List<String> list = imgFreeTestMapper.selectListById(studentFreeTest.getId());
            data.put("studentFreeTest",studentFreeTest);
            data.put("images",list);
            res.add(data);
        }
        return new Result(CommonCode.SUCCESS,res);
    }

    @Override
    public Result rejectApplication(FreeTestDTO freeTestDTO) {
        //通过查询id查询studentFreeTest信息
        StudentFreeTest studentFreeTest = studentFreeTestMapper.selectById(freeTestDTO.getId());
        //设置为不通过，并更新数据库
        studentFreeTest.setIsPass("2");
        studentFreeTest.setAuditMessage(freeTestDTO.getAuditMessage());
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        studentFreeTest.setAuditTime(sdf.format(data));
        studentFreeTest.setAuditMessage(freeTestDTO.getAuditMessage());
        //未读
        studentFreeTest.setIsRead(1);
        studentFreeTest.setHandler(UserUtils.getUser().getName());
        int i = studentFreeTestMapper.updateById(studentFreeTest);
        if (i > 0){
            return new Result(CommonCode.SUCCESS);
        }
        return new Result(CommonCode.FAILURE);
    }

    @Override
    public Result hasMessage() {
        Long userId = UserUtils.getUserId();
        List<StudentFreeTest> studentFreeTests = studentFreeTestMapper.hasMessage(userId);
        System.out.println(studentFreeTests.size());
        if (studentFreeTests.size() != 0) {
            return new Result(CommonCode.SUCCESS,true);
        }
        return new Result(CommonCode.SUCCESS,false);
    }

    @Override
    public Result freeTestById(Long id) {
        StudentFreeTest studentFreeTest1 = studentFreeTestMapper.selectById(id);
        String isPassed = studentFreeTest1.getIsPass();
        if (("1".equals(isPassed) || "2".equals(isPassed)) && studentFreeTest1.getUserId().equals(UserUtils.getUserId())) {
            studentFreeTest1.setIsRead(2);
            //标记为已读
            studentFreeTestMapper.updateById(studentFreeTest1);
        }
        return new Result(CommonCode.SUCCESS);
    }
}
