package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@TableName(value = "t_config")
public class Config implements Serializable {
    private static final long serialVersionUID = 830247103594848910L;
    @TableId(type = IdType.AUTO)
    private Long id;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modified;
    // 版本号
    private Integer version;
    //password
    private String passPrefix;
    // token
    private String tokenName;
    private String tokenSecret;
    private Integer tokenExp;
    //email
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
    private Integer trackerInterval;
    private Integer trackerMinInterval;
    // web img
    private String webHeadImgUrl;
    private Boolean isDelete;
}
