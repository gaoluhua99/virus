package com.virus.pt.model.bo;

import com.virus.pt.model.dataobject.Config;
import com.virus.pt.model.dataobject.UserLevel;
import com.virus.pt.model.dto.*;
import com.virus.pt.model.util.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class ConfigBo {
    public static ConfigDto getConfigDto(Config config) {
        ConfigDto configDTO = new ConfigDto();
        BeanUtils.copyFieldToBean(config, configDTO);
        return configDTO;
    }

    public static ConfigUserDto getConfigUserDto(Config config, List<UserLevel> levelList,
                                                 List<PostCategoryDto> postCategoryDtoList) {
        ConfigUserDto configUserDto = new ConfigUserDto();
        BeanUtils.copyFieldToBean(config, configUserDto);
        List<UserLevelDto> userLevelDtoList = new ArrayList<>();
        levelList.forEach(level -> {
            UserLevelDto userLevelDto = new UserLevelDto();
            userLevelDto.setLevelName(level.getLevelName());
            userLevelDto.setNeedExp(level.getNeedExp());
            userLevelDtoList.add(userLevelDto);
        });
        configUserDto.setLevelList(userLevelDtoList);
        configUserDto.setPostCategoryList(postCategoryDtoList);
        return configUserDto;
    }

    public static SiteInfoDto getSiteInfoDto(int total, int warning, int ban, int notActive,
                                             int male, int female) {
        SiteInfoDto siteInfoDto = new SiteInfoDto();
        siteInfoDto.setTotalUser(total);
        siteInfoDto.setWarningUser(warning);
        siteInfoDto.setBangUser(ban);
        siteInfoDto.setNotActiveUser(notActive);
        siteInfoDto.setMaleUser(male);
        siteInfoDto.setFemaleUser(female);
        return siteInfoDto;
    }
}
