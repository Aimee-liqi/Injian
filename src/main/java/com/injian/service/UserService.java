package com.injian.service;

import com.injian.error.BusinessException;
import com.injian.service.model.UserModel;

public interface UserService {
    //通过用户id获取用户对象的方法
    UserModel getUserById(Integer id);
    //用户注册
    void register(UserModel userModel) throws BusinessException;
    UserModel validatelogin(String telphone,String encrptPassword) throws BusinessException;

    void updateUserInfo(UserModel userModel) throws BusinessException;
    //上传图片
    void updateImg(Integer userId,String path) throws BusinessException;
    //更新密码
    void updatePassword(Integer userId,String encrptPassword) throws BusinessException;
    void validateTelphone(String telphone) throws BusinessException;
}
