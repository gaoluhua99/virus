package com.virus.pt.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description = "全局配置")
@Getter
@Setter
@ToString
public class ConfigDto {
    @ApiModelProperty(value = "版本号", example = "1")
    private Integer version;

    @ApiModelProperty(value = "邀请功能状态", example = "true")
    private Boolean inviteState;

    @ApiModelProperty(value = "登陆功能状态", example = "true")
    private Boolean loginState;

    @ApiModelProperty(value = "注册功能状态", example = "true")
    private Boolean registerState;
}
