package com.wx.app.enums;

/**
 * @author lingqu
 * @date 2022/3/11
 * @apiNote
 */
public enum SexEnum {
    /**
     * 男
     */
    MAN(0,"男"),
    /**
     * 女
     */
    WOMAN(1,"女");

    private Integer sex;
    private String sexName;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }


    SexEnum(Integer sex, String sexName) {
        this.sex = sex;
        this.sexName = sexName;
    }
}
