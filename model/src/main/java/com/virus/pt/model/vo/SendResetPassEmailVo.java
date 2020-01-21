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
@ApiModel(description = "重置密码发送邮件时需要的参数model")
@Getter
@Setter
@ToString
public class SendResetPassEmailVo {
    @ApiModelProperty(value = "email", example = "xxx@qq.com")
    private String email;

    @ApiModelProperty(value = "验证码", required = true)
    private String captcha;
}
