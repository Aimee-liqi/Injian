package com.injian.service.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserModel {
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotNull(message = "性别必填")
    private Byte gender;
    @NotBlank(message = "手机号不能为空")
    private String telphone;
    @NotBlank(message = "邮箱不能为空")
    private String email;
    private String imgUrl;
    @NotBlank(message = "密码不能为空")
    private String encrptpassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEncrptpassword() {
        return encrptpassword;
    }

    public void setEncrptpassword(String encrptpassword) {
        this.encrptpassword = encrptpassword;
    }
}
