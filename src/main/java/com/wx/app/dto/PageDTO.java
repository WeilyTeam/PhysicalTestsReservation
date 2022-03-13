package com.wx.app.dto;/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {
    private Long size;
    private Long current;
}
