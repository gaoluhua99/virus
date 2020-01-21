package com.virus.pt.core.service;

import com.virus.pt.common.exception.TipException;
import com.virus.pt.model.dto.UserDto;
import com.virus.pt.model.vo.RegisterVo;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 2:13 下午
 * @email zzy.main@gmail.com
 */
public interface UserService {
    /**
     * 创建用户
     *
     * @param registerVO 用户参数
     * @return 是否创建完成
     * @throws com.virus.pt.common.exception.TipException
     * @throws DataAccessException 唯一键约束，已经有了该Email或该昵称
     */
    @Transactional(rollbackFor = Exception.class)
    boolean saveUser(RegisterVo registerVO) throws TipException, DataAccessException;

    /**
     * 根据email和password获取用户，即用户登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return 用户
     * @throws com.virus.pt.common.exception.TipException
     */
    UserDto getUserByLogin(String email, String password) throws TipException;

    UserDto getUserById(int uid) throws TipException;

    /**
     * 存储激活码
     *
     * @param code  激活码
     * @param email email
     * @throws com.virus.pt.common.exception.TipException
     */
    void saveActivationCodeToRedis(String code, String email) throws TipException;

    /**
     * 激活用户
     * 先判断激活码是否正确
     * 激活用户后删除该键
     *
     * @param code 激活码
     * @return 用户
     * @throws com.virus.pt.common.exception.TipException
     */
    boolean activateUser(String code) throws TipException;

    /**
     * 存储重置密码认证码到redis
     *
     * @param code  认证码
     * @param email 邮箱
     */
    void saveResetPassCode(String code, String email) throws TipException;

    /**
     * 判断是否存在认证码
     *
     * @param code 认证码
     * @return email
     */
    String existResetPassCode(String code) throws TipException;

    void removeResetPassCode(String code);
}
