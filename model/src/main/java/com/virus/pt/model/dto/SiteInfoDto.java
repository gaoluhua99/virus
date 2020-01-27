package com.virus.pt.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/26 12:55 下午
 * @email zzy.main@gmail.com
 */
@ApiModel(description = "站点信息")
@Getter
@Setter
public class SiteInfoDto {
    @ApiModelProperty(value = "总注册人数", example = "100")
    private Integer totalUser;

    @ApiModelProperty(value = "警告人数", example = "100")
    private Integer warningUser;

    @ApiModelProperty(value = "ban人数", example = "100")
    private Integer bangUser;

    @ApiModelProperty(value = "未激活人数", example = "100")
    private Integer notActiveUser;
}
