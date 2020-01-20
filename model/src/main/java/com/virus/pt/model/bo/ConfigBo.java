package com.virus.pt.model.bo;

import com.virus.pt.model.dataobject.Config;
import com.virus.pt.model.dto.ConfigDto;
import com.virus.pt.model.util.BeanUtils;

public class ConfigBo {
    public static ConfigDto getConfigDto(Config config) {
        ConfigDto configDTO = new ConfigDto();
        BeanUtils.copyFieldToBean(config, configDTO);
        return configDTO;
    }
}
