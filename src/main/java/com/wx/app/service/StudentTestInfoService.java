package com.wx.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.dto.PageDTO;
import com.wx.app.entity.StudentTestInfo;

/**
*
*/
public interface StudentTestInfoService extends IService<StudentTestInfo> {

    Page<StudentTestInfo> getTestList(PageDTO pageDTO);

    StudentTestInfo checkStockById(Long id);

    void updateOrderNum(StudentTestInfo studentTestInfo);
}
