package com.wx.app.service;

import com.wx.app.dto.PageDTO;
import com.wx.app.dto.StuPwdDTO;
import com.wx.app.dto.StudentInfoDTO;
import com.wx.app.dto.TeacherDTO;
import com.wx.app.entity.User;
import com.wx.app.utils.Result;
import com.wx.app.vo.TeacherInfoVo;

/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */
public interface UserService {
    Result resetStuPwd(Long id);

    Result getStudentList(PageDTO pageDTO, StudentInfoDTO studentTestInfo);
    Result getStudentMonitorList(PageDTO pageDTO, StudentInfoDTO studentTestInfo);

    Result getStudentInfo(Long userId);

    Result deleteStudent(Long userId);

    //Result getstuInfo();

    Result updateStuPwd(StuPwdDTO stuPwdDTO);

    Result getTeacherList(PageDTO pageDTO, TeacherDTO teacherDTO);

    Result addTeacher(TeacherInfoVo user);

    Result updateTeacher(TeacherInfoVo user);

    Result deleteTeacher(Long id, String userName);

    Result getTeacherById(Long id);

    Result addStudent(User user);

    Result updateStudent(User user);

    Result studentToMonitor(Long userId);

    Result studentToMonitorBack(Long userId);

    Result getStudentsByClass(PageDTO pageDTO, String specialtyClass);
}
