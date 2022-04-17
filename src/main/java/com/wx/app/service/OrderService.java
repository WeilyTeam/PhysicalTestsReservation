package com.wx.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.app.dto.OrderDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.dto.TestListCondition;
import com.wx.app.dto.PageDTO;
import com.wx.app.entity.StudentTestInfo;
import com.wx.app.utils.Result;

/**
 * @author lingqu
 * @date 2022/3/9
 * @apiNote
 */


public interface OrderService {
    Result orderByid(OrderDTO orderDTO);

    Page<StudentTestInfo> testList(PageDTO pageDTO, TestListCondition testListCondition);
    Page<StudentTestInfo> allTestList(PageDTO pageDTO, TestListCondition testListCondition);

    Result orderList(PageDTO pageDTO, Long id, StudentInfoDTO studentTestInfo);

    Result addTest(StudentTestInfo studentTestInfo);

    Result deleteTestById(Long id);

    Result deleteOrderById(Long id);

    Result getOrderInfo();

    Result updateTest(StudentTestInfo studentTestInfo);

}
