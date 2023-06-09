package com.mtx.mall.service;

import com.mtx.mall.model.vo.CartVO;

import java.util.List;

/**
 * 描述： 邮件Service
 * */
public interface EmailService {


    void sendSimpleMessage(String to, String subject, String text);

    Boolean saveEmailToRedis(String emailAddress, String verificationCode);
}
