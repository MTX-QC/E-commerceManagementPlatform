package com.mtx.cloud.mall.user.service.impl;


import com.mtx.cloud.mall.common.exception.MtxMallException;
import com.mtx.cloud.mall.common.exception.MtxMallExceptionEnum;
import com.mtx.cloud.mall.common.util.MD5Utils;
import com.mtx.cloud.mall.user.model.dao.UserMapper;

import com.mtx.cloud.mall.user.model.pojo.User;
import com.mtx.cloud.mall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * 描述：   UserService实现类
 * */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;


    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }

    @Override
    public void register(String userName, String password,String emailAddress) throws MtxMallException {
        //查询用户名是否存在，不允许重名
        User result = userMapper.selectByName(userName);
        if (result != null){
            throw new MtxMallException(MtxMallExceptionEnum.NAME_EXISTED);
        }
        //写到数据库
        User user = new User();
        user.setUsername(userName);
        user.setEmailAddress(emailAddress);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int count = userMapper.insertSelective(user);
        if (count==0){
            throw new MtxMallException(MtxMallExceptionEnum.INSERT_FAILED);
        }
    }
    @Override
    public User login(String userName, String password) throws MtxMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        User user = userMapper.selectLogin(userName, md5Password);
        if (user == null){
            throw new MtxMallException(MtxMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }
    @Override
    public void updateInformation(User user) throws MtxMallException{
        //更新个性签名
        int updateCount = userMapper.updateByPrimaryKeySelective(user);

        if (updateCount > 1){
            throw new MtxMallException(MtxMallExceptionEnum.UPDATE_FAILED);
        }
    }

    //判断登录的是否是管理员
    @Override
    public boolean checkAdminRole(User user){
        //1是普通用户，2是管理员
        return user.getRole().equals(2);
    }

    /*
     *邮箱是否重复
     * */
    @Override
    public boolean checkEmailRegistered(String emailAddress) {
        User user = userMapper.selectOneByEmailAddress(emailAddress);
        if (user != null){
            return false;
        }
        return true;
    }





}
