package com.wx.app.service;

import com.wx.app.dto.PageDTO;
import com.wx.app.utils.Result;

/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */
public interface UserService {
    Result resetStuPwd(Long id);

    Result getStudentList(PageDTO pageDTO);

    Result getStudentInfo(Long userId);

    Result deleteStudent(Long userId);

    Result getstuInfo();

}