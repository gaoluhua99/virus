package com.virus.pt.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/20 5:02 下午
 * @email zzy.main@gmail.com
 */
@ApiModel(description = "已登录用户获取配置的模型")
@Getter
@Setter
@ToString
public class ConfigUserDto {
    @ApiModelProperty(value = "版本号", example = "1")
    private Integer version;

    @ApiModelProperty(value = "web页面头部图片url", example = "http://i0.hdslb.com/bfs/archive/132f3cdb797daf3a1577993720d21c7d1bc9d5f0.png")
    private String webHeadImgUrl;

    @ApiModelProperty(value = "等级表")
    private List<UserLevelDto> levelList;
}
