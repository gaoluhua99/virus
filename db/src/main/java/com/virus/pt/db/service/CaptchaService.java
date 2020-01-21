package com.virus.pt.db.service;

import com.virus.pt.common.exception.TipException;

public interface CaptchaService {
    /**
     * 存储验证码
     *
     * @param ip      ip地址
     * @param captcha 验证码
     * @throws TipException 抛出异常
     */
    void saveCaptcha(String ip, String captcha) throws TipException;

    /**
     * 移除验证码
     *
     * @param ip ip地址
     * @return remove true
     */
    boolean removeCaptcha(String ip);

    /**
     * 验证验证码是否正确
     *
     * @param ip      ip地址
     * @param captcha 输入的验证码
     * @return true则正确
     * @throws TipException 抛出异常
     */
    boolean verifyCaptcha(String ip, String captcha) throws TipException;
}
