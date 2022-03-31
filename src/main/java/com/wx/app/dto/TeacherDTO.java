package com.wx.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lingqu
 * @date 2022/3/31
 * @apiNote
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private String userName;
    private String name;
    private String college;
}
