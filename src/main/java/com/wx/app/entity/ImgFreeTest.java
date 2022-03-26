package com.wx.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @TableName img_free_test
 */
@TableName(value ="img_free_test")
@Data
public class ImgFreeTest implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     *
     */
    private String imageRoute;

    /**
     *
     */
    private Long freeTestId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
