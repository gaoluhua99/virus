package com.virus.pt.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/20 2:22 下午
 * @email zzy.main@gmail.com
 */
@ApiModel(description = "注册时需要的参数model")
@Getter
@Setter
@ToString
public class RegisterVo {
    @ApiModelProperty(value = "邮箱", example = "xxx@gmail.com", required = true)
    private String email;

    @ApiModelProperty(value = "密码", example = "123456ab", required = true)
    private String password;

    @ApiModelProperty(value = "昵称", example = "intent", required = true)
    private String username;

    @ApiModelProperty(value = "性别", example = "true", required = false)
    private Boolean sex;

    @ApiModelProperty(value = "验证码", required = true)
    private String captcha;

    @ApiModelProperty(value = "注册时的ip", example = "127.0.0.1", required = false)
    private String ip;
}
