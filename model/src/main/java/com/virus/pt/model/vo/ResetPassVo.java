package com.virus.pt.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @date 2019/7/20 20:32
 * @about <link href='http://zzyitj.xyz/'/>
 */
@ApiModel(description = "重置密码时需要的参数model")
@Getter
@Setter
@ToString
public class ResetPassVo {
    @ApiModelProperty(value = "密码", example = "123456ab", required = true)
    private String password;

    @ApiModelProperty(value = "认证码", required = true)
    private String code;

    @ApiModelProperty(value = "验证码", required = true)
    private String captcha;
}
