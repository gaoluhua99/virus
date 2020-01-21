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
@ApiModel(description = "获取IPv6时需要的参数model")
@Getter
@Setter
@ToString
public class IPEndpointVo {
    @ApiModelProperty(value = "IP地址", example = "127.0.0.1", required = true)
    private String host;
    @ApiModelProperty(value = "端口", example = "80", required = true)
    private String port;

    public IPEndpointVo(String host, String port) {
        this.host = host;
        this.port = port;
    }
}
