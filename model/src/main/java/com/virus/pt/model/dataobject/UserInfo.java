package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

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
    private Long id;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modified;
    private Long fkUserAuthId;
    private Long fkUserDataId;
    private String ukUsername;
    private Boolean sex;
    private Long gold;
    private Long exp;
    private Long inviterId;
    private String avatarUrl;
    private String ip;
    // 是否删除
    private Boolean isDelete;
}
