package com.wx.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.dto.PageDTO;
import com.wx.app.entity.StudentFreeTest;
import com.wx.app.utils.Result;

/**
*
*/
public interface StudentFreeTestService extends IService<StudentFreeTest> {
    Result freeTestList(PageDTO pageDTO);

    Result freeTestApplication(StudentFreeTest studentFreeTest);

    Result agreeApplication(Long id);

    Result deleteFreeTest(Long id);
}
