package com.virus.pt.model.bo;

import com.virus.pt.model.dataobject.UserAuth;
import com.virus.pt.model.dataobject.UserData;
import com.virus.pt.model.dataobject.UserInfo;
import com.virus.pt.model.dto.UserDto;
import com.virus.pt.model.util.BeanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 9:20 下午
 * @email zzy.main@gmail.com
 */
public class UserBo {
    public static UserDto getUserDto(UserAuth userAuth, UserData userData, UserInfo userInfo, String token) {
        UserDto userDTO = new UserDto();
        if (userAuth != null) {
            userDTO.setUserAuthId(userAuth.getId());
            userDTO.setEmail(userAuth.getUkEmail());
            userDTO.setIsActivation(userAuth.getIsActivation());
        }
        if (userData != null) {
            userDTO.setCreate(userData.getCreated().getTime());
            userDTO.setModify(userData.getModified().getTime());
            userDTO.setPasskey(userData.getUkPasskey());
            userDTO.setUploaded(userData.getUploaded());
            userDTO.setDownloaded(userData.getDownloaded());
            userDTO.setStatus(userData.getUserStatus());
        }
        if (userInfo != null) {
            BeanUtils.copyFieldToBean(userInfo, userDTO);
            userDTO.setUsername(userInfo.getUkUsername());
        }
        if (StringUtils.isNotBlank(token)) {
            userDTO.setToken(token);
        }
        return userDTO;
    }
}
