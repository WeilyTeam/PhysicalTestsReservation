package com.wx.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 * @TableName sys_role
 */
@TableName(value ="sys_role")
@Data
public class SysRole implements Serializable {
    /**
     *
     */
    @TableId
    private String name;

    private String roleKey;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
