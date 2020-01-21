package com.virus.pt.model.bo;

import com.virus.pt.model.dataobject.UserAuth;
import com.virus.pt.model.dataobject.UserData;
import com.virus.pt.model.dataobject.UserInfo;
import com.virus.pt.model.util.BeanUtils;
import com.virus.pt.model.vo.RegisterVo;

public class RegisterBo {
    public static UserAuth getUserAuth(String passwordHash, String email) {
        UserAuth userAuth = new UserAuth();
        userAuth.setPasswordHash(passwordHash);
        userAuth.setUkEmail(email);
        return userAuth;
    }

    public static UserData getUserData(String passkey) {
        UserData userData = new UserData();
        userData.setUkPasskey(passkey);
        return userData;
    }

    public static UserInfo getUserInfo(RegisterVo registerVO, long userAuthId, long userDataId) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyFieldToBean(registerVO, userInfo);
        userInfo.setFkUserAuthId(userAuthId);
        userInfo.setFkUserDataId(userDataId);
        userInfo.setUkUsername(registerVO.getUsername());
        if (registerVO.getSex() == null) {
            userInfo.setSex(true);
        }
        return userInfo;
    }
}
