package com.mtx.mall.service;

import com.mtx.mall.model.vo.CartVO;

import java.util.List;

/**
 * 描述： 邮件Service
 * */
public interface EmailService {
    /*
    * to 发给谁  subject  主题      text  正文
    * */
    void sendSimpleMessage(String to, String subject, String text);

    Boolean saveEmailToRedis(String emailAddress, String verificationCode);

    /*
     * 判断邮箱和验证码是否匹配
     * */
    Boolean checkEmailAndCode(String emailAddress, String verificationCode);
}
