package com.wx.app.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lingqu
 * @date 2022/3/29
 * @apiNote
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreeTestDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String auditMessage;
}
