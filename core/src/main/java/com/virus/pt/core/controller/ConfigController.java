package com.virus.pt.core.controller;

import com.virus.pt.common.constant.ApiConst;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.db.service.ConfigService;
import com.virus.pt.db.service.LevelService;
import com.virus.pt.model.bo.ConfigBo;
import com.virus.pt.model.dto.ConfigDto;
import com.virus.pt.model.dto.ConfigUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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

    @Autowired
    private LevelService levelService;

    /**
     * 获取配置参数
     *
     * @return {@link com.virus.pt.model.dto.ConfigDto}
     */
    @ApiOperation(value = "获取配置参数")
    @GetMapping(value = "")
    public ResponseEntity<ConfigDto> getConfig() throws TipException {
        return ResponseEntity.ok(ConfigBo.getConfigDto(configService.get()));
    }

    /**
     * 已登录的用户获取配置
     *
     * @return {@link com.virus.pt.model.dto.ConfigUserDto}
     */
    @ApiOperation(value = "已登录的用户获取配置")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @GetMapping(value = "${config.virus.url.config.user}")
    public ResponseEntity<ConfigUserDto> getConfigUser() throws TipException {
        return ResponseEntity.ok(
                ConfigBo.getConfigUserDto(configService.get(), levelService.getAll()));
    }

    // TODO 管理员更新配置
}
