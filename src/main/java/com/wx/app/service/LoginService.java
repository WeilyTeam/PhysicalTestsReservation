package com.wx.app.service;/**
 * @author lingqu
 * @date 2022/3/1
 * @apiNote
 */

import com.wx.app.dto.StuPwdDTO;
import com.wx.app.dto.UserDTO;
import com.wx.app.entity.User;
import com.wx.app.utils.Result;

public interface LoginService {
    Result login(UserDTO userDTO);
    Result loginAdmin(UserDTO userDTO);
    Result logout();

    Result register(User user);

    Result refreshToken();


    Result userInfo();

    Result resetStuPwd(Long userId);

    Result updateStuPwd(StuPwdDTO stuPwdDTO);


}
