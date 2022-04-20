package com.wx.app.dto;/**
 * @author lingqu
 * @date 2022/3/9
 * @apiNote
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String specialtyClass;
    private Long testId;
}
