package com.wx.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.dto.*;
import com.wx.app.entity.StudentFreeTest;
import com.wx.app.utils.Result;

/**
*
*/
public interface StudentFreeTestService extends IService<StudentFreeTest> {
    Result freeTestList(PageDTO pageDTO, StudentInfoDTO studentTestInfo, AuditDOT auditDOT);

    Result freeTestApplication(StudentFreeTestDTO studentFreeTestDTO);

    Result agreeApplication(FreeTestDTO freeTestDTO);

    Result deleteFreeTest(Long id);

    Result freeTestByToken(Long userId);

    Result rejectApplication(FreeTestDTO freeTestDTO);

    Result hasMessage();

    Result freeTestById(Long id);
}
