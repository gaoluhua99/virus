package com.virus.pt.core.service.impl;

import com.virus.pt.common.constant.RedisConst;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.CheckUtils;
import com.virus.pt.common.util.VirusUtils;
import com.virus.pt.core.service.UserService;
import com.virus.pt.db.service.UserAuthService;
import com.virus.pt.db.service.UserDataService;
import com.virus.pt.db.service.UserInfoService;
import com.virus.pt.model.bo.RegisterBo;
import com.virus.pt.model.dataobject.UserAuth;
import com.virus.pt.model.dataobject.UserData;
import com.virus.pt.model.dataobject.UserInfo;
import com.virus.pt.model.dto.UserDto;
import com.virus.pt.model.vo.RegisterVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 2:20 下午
 * @email zzy.main@gmail.com
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 创建用户
     * 1. 插入t_user_auth表
     * 2. 插入t_user_data表
     * 3. 获取插入的t_user_auth的id和插入的t_user_data表的id并插入t_user_info表
     *
     * @param registerVO 用户参数
     * @return
     * @throws TipException
     * @throws DataAccessException
     */
    @Override
    public boolean saveUser(RegisterVo registerVO) throws TipException, DataAccessException {
        CheckUtils.checkEmailAndUsername(registerVO.getEmail(), registerVO.getUsername());
        CheckUtils.checkPassword(registerVO.getPassword());
        try {
            String passwordHash = DigestUtils.md5Hex(VirusUtils.config.getPassPrefix() + registerVO.getPassword());
            UserAuth userAuth = RegisterBo.getUserAuth(passwordHash, registerVO.getEmail());
            // 插入t_user_auth表
            if (userAuthService.saveRollback(userAuth)) {
                // 插入t_user_data表
                UserData userData = RegisterBo.getUserData(VirusUtils.randomCode());
                if (userDataService.saveRollback(userData)) {
                    // 插入t_user_info表
                    return userInfoService.saveRollback(
                            RegisterBo.getUserInfo(registerVO, userAuth.getId(), userData.getId()));
                }
            }
            throw new TipException(ResultEnum.ACCOUNT_CREATE_ERROR);
        } catch (DataAccessException e) {
            logger.info("重复注册, 唯一键约束, email: {}, username: {}", registerVO.getEmail(), registerVO.getUsername());
            throw new TipException(ResultEnum.REGISTER_ERROR);
        }
    }

    @Override
    public UserDto getUserByLogin(String email, String password) throws TipException {
        return null;
    }

    @Override
    public UserDto getUserById(int uid) throws TipException {
        return null;
    }

    @Override
    public void saveActivationCodeToRedis(String code, String email) throws TipException {
        CheckUtils.checkEmail(email);
        if (StringUtils.isBlank(code)) {
            throw new TipException(ResultEnum.ACTIVATION_CODE_EMPTY_ERROR);
        } else {
            valueOperations.set(RedisConst.ACTIVATION_CODE_PREFIX + code,
                    email, Duration.ofSeconds(RedisConst.ACTIVATION_CODE_EXP));
        }
    }

    @Override
    public boolean activateUser(String code) throws TipException {
        if (StringUtils.isBlank(code)) {
            throw new TipException(ResultEnum.ACTIVATION_CODE_EMPTY_ERROR);
        }
        String email = (String) valueOperations.get(RedisConst.ACTIVATION_CODE_PREFIX + code);
        if (StringUtils.isBlank(email)) {
            throw new TipException(ResultEnum.ACTIVATION_CODE_ERROR);
        }
        boolean isActivation = userAuthService.updateActive(email);
        if (isActivation) {
            redisTemplate.delete(RedisConst.ACTIVATION_CODE_PREFIX + code);
        }
        return isActivation;
    }

    @Override
    public void saveResetPassCode(String code, String email) throws TipException {

    }

    @Override
    public String existResetPassCode(String code) throws TipException {
        return null;
    }

    @Override
    public void removeResetPassCode(String code) {

    }
}
