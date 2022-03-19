package com.wx.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lingqu
 * @date 2022/3/19
 * @apiNote
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StuPwdDTO {
    private String prePassword;
    private String newPassword;
}
