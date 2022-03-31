package com.wx.app.service;

import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StuPwdDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.entity.User;
import com.wx.app.utils.Result;

/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */
public interface UserService {
    Result resetStuPwd(Long id);

    Result getStudentList(PageDTO pageDTO, StudentInfoDTO studentTestInfo);

    Result getStudentInfo(Long userId);

    Result deleteStudent(Long userId);

    //Result getstuInfo();

    Result updateStuPwd(StuPwdDTO stuPwdDTO);

    Result getTeacherList(PageDTO pageDTO);

    Result addTeacher(User user);

    Result updateTeacher(User user);

    Result deleteTeacher(Long id, String userName);
}
