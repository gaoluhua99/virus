package com.virus.pt.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @date 2019/7/25 16:20
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Getter
@Setter
@ToString
public class IPEndpointVo {
    private String host;
    private String port;

    public IPEndpointVo(String host, String port) {
        this.host = host;
        this.port = port;
    }
}
