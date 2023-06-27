package com.mtx.mall.service;

import com.mtx.mall.exception.MtxMallException;
import com.mtx.mall.model.pojo.User;
import org.springframework.data.repository.query.Param;

/**
 * 描述：  UserService
 * */


public interface UserService {
    User getUser();

    void register(String userName, String password, String emailAddress) throws MtxMallException;

    User login(String userName, String password) throws MtxMallException;

    void updateInformation(User user) throws MtxMallException;

    boolean checkAdminRole(User user);

    boolean checkEmailRegistered(String emailAddress);
}
