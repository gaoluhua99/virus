package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author intent
 * @date 2019/7/14 15:53
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Getter
@Setter
@ToString
@TableName(value = "t_user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -3447984729338161227L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private String passkey;
    private String username;
    private Boolean sex;
    private Long uploaded;
    private Long downloaded;
    private Long gold;
    private Long diamond;
    private Long exp;
    private Integer inviterId;
    private String avatarUrl;
    private String ip;
    @TableField(fill = FieldFill.INSERT)
    private Long created;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long modify;
    private Integer status;
}
