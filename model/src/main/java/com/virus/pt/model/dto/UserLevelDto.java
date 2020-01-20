package com.virus.pt.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/20 5:41 下午
 * @email zzy.main@gmail.com
 */
@ApiModel(description = "用户等级")
@Getter
@Setter
@ToString
public class UserLevelDto {
    @ApiModelProperty(value = "需要的经验", example = "1")
    private Long needExp;
    @ApiModelProperty(value = "等级名字", example = "初级粉丝")
    private String levelName;
}
