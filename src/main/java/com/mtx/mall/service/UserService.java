package com.mtx.mall.service;

import com.mtx.mall.exception.MtxMallException;
import com.mtx.mall.model.pojo.User;
/**
 * 描述：  UserService
 * */


public interface UserService {
    User getUser();

    void register(String username,String password) throws MtxMallException;

    User login(String userName, String password) throws MtxMallException;

    void updateInformation(User user) throws MtxMallException;

    //判断登录的是否是管理员
    boolean checkAdminRole(User user);

    boolean checkEmailRegistered(String emailAddress);
}
