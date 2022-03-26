package com.wx.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.entity.StudentTest;
import com.wx.app.utils.Result;

/**
*
*/
public interface StudentTestService extends IService<StudentTest> {

    void createOrder(StudentTest studentTest);

    StudentTest checkOrder(Long id, String semester);

    Result getTestList(PageDTO pageDTO, Long id, StudentInfoDTO studentTestInfo);

}
