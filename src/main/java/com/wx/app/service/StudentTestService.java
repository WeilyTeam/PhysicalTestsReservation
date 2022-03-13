package com.wx.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.dto.PageDTO;
import com.wx.app.entity.StudentTest;

import java.util.List;

/**
*
*/
public interface StudentTestService extends IService<StudentTest> {

    void createOrder(StudentTest studentTest);

    StudentTest checkOrder(Long id, String semester);

    Page<StudentTest> getTestList(PageDTO pageDTO, Long id);

}
