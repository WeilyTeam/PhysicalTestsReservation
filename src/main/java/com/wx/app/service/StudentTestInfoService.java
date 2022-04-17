package com.wx.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.dto.TestListCondition;
import com.wx.app.dto.PageDTO;
import com.wx.app.entity.StudentTestInfo;

/**
*
*/
public interface StudentTestInfoService extends IService<StudentTestInfo> {

    Page<StudentTestInfo> getTestList(PageDTO pageDTO, TestListCondition testListCondition);
    Page<StudentTestInfo> getAllTestList(PageDTO pageDTO, TestListCondition testListCondition);

    StudentTestInfo checkStockById(Long id);

    void updateOrderNum(StudentTestInfo studentTestInfo);

    void decOrder(Long testId);

}
