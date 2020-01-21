package com.virus.pt.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description = "登录时需要的参数model")
@Getter
@Setter
@ToString
public class LoginVo {
    @ApiModelProperty(value = "邮箱", example = "xxx@gmail.com", required = true)
    private String email;

    @ApiModelProperty(value = "密码", example = "123456ab", required = true)
    private String password;

    @ApiModelProperty(value = "验证码", required = true)
    private String captcha;
}
