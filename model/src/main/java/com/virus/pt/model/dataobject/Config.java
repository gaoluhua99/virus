package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@TableName(value = "t_config")
public class Config implements Serializable {
    private static final long serialVersionUID = 830247103594848910L;
    @TableId(type = IdType.AUTO)
    private Long id;
    //password
    private String passPrefix;
    // token
    private String tokenName;
    private String tokenSecret;
    private Integer tokenExp;
    //email
    private String emailName;
    private String emailPass;
    private String emailSmtpHost;
    private Integer emailSmtpPort;
    private String emailNickName;
    private String emailSubject;
    private String emailActivationTemplate;
    private String emailInviteTemplate;
    private String emailResetPassTemplate;
    // 状态
    private Boolean inviteState;
    private Boolean loginState;
    private Boolean registerState;
    // imdb
    private String imdbKey;
    private Boolean isDelete;
}
