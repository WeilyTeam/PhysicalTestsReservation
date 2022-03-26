package com.wx.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StudentFreeTestDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.entity.StudentFreeTest;
import com.wx.app.utils.Result;

/**
*
*/
public interface StudentFreeTestService extends IService<StudentFreeTest> {
    Result freeTestList(PageDTO pageDTO, StudentInfoDTO studentTestInfo);

    Result freeTestApplication(StudentFreeTestDTO studentFreeTestDTO);

    Result agreeApplication(Long id);

    Result deleteFreeTest(Long id);
}
