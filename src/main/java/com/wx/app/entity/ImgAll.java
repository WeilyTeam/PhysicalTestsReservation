package com.wx.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @TableName img_all
 */
@TableName(value ="img_all")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgAll implements Serializable {
    /**
     *
     */

    private String imgRoute;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
