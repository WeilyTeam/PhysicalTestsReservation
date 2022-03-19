package com.wx.app.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wx.app.dto.OrderDTO;
import com.wx.app.dto.PageDTO;
import com.wx.app.entity.StudentTest;
import com.wx.app.entity.StudentTestInfo;
import com.wx.app.utils.Result;

/**
 * @author lingqu
 * @date 2022/3/9
 * @apiNote
 */


public interface OrderService {
    void orderByid(OrderDTO orderDTO);

    Page<StudentTestInfo> testList(PageDTO pageDTO);

    Page<StudentTest> orderList(PageDTO pageDTO, Long id);

    Result addTest(StudentTestInfo studentTestInfo);

    Result deleteTestById(Long id);

    Result deleteOrderById(Long id);

    Result getOrderInfo();

}
