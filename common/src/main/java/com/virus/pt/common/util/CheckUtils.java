package com.virus.pt.common.util;

import com.virus.pt.common.constant.ApiConst;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckUtils {
    private static final Logger logger = LoggerFactory.getLogger(CheckUtils.class);

    public static void checkEmailAndUsername(String email, String username) throws TipException {
        checkEmail(email);
        checkUsername(username);
    }

    public static void checkPasskey(String passkey) throws TipException {
        if (StringUtils.isNotBlank(passkey)) {
            if (passkey.length() != 32) {
                throw new TipException(ResultEnum.PASSKEY_LEN_ERROR);
            }
        }
    }

    /**
     * 检查昵称规则
     *
     * @param username
     * @throws TipException
     */
    public static void checkUsername(String username) throws TipException {
        // 用户名为空
        if (StringUtils.isBlank(username)) {
            throw new TipException(ResultEnum.USERNAME_EMPTY_ERROR);
        }
        // 用户名长度错误
        if (username.length() < ApiConst.USERNAME_MIN_LEN
                || username.length() > ApiConst.USERNAME_MAX_LEN) {
            throw new TipException(ResultEnum.USERNAME_LEN_ERROR);
        }
//        if (!RegexUtils.isUsername(username)) {
//            throw new TipException(ResultEnum.NICKNAME_FORMAT_ERROR);
//        }
    }

    /**
     * 检查邮箱规则
     *
     * @param email
     * @throws TipException
     */
    public static void checkEmail(String email) throws TipException {
        if (StringUtils.isBlank(email)) {
            throw new TipException(ResultEnum.EMAIL_EMPTY_ERROR);
        }
        // 邮箱格式错误
        if (!RegexUtils.isEmail(email)) {
            throw new TipException(ResultEnum.EMAIL_FORMAT_ERROR);
        }
        // 邮箱长度错误
        if (email.length() > ApiConst.EMAIL_MAX_LEN) {
            throw new TipException(ResultEnum.EMAIL_LEN_ERROR);
        }
    }

    /**
     * 检查密码规则
     *
     * @param password 密码
     * @throws TipException
     */
    public static void checkPassword(String password) throws TipException {
        if (StringUtils.isBlank(password)) {
            throw new TipException(ResultEnum.PASSWORD_EMPTY_ERROR);
        }
        // 密码长度错误
        if (password.length() < ApiConst.PASSWORD_MIN_LEN
                || password.length() > ApiConst.PASSWORD_MAX_LEN) {
            throw new TipException(ResultEnum.PASSWORD_LEN_ERROR);
        }
    }
}
