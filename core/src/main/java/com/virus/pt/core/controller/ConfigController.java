package com.virus.pt.core.controller;

import com.virus.pt.common.exception.TipException;
import com.virus.pt.db.service.ConfigService;
import com.virus.pt.model.bo.ConfigBo;
import com.virus.pt.model.dto.ConfigDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "获取配置、更新配置、获取公告")
@RestController
@RequestMapping("${config.virus.url.config.value}")
public class ConfigController {
    @Autowired
    private ConfigService configService;

    /**
     * 获取配置参数
     *
     * @return {@link ConfigDto}
     */
    @ApiOperation(value = "获取配置参数")
    @GetMapping(value = "")
    public ResponseEntity<ConfigDto> getConfig() throws TipException {
        return ResponseEntity.ok(ConfigBo.getConfigDto(configService.getConfig()));
    }

    // TODO 管理员更新配置
}
