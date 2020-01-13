package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/10 11:34 上午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
@TableName(value = "t_user_auth")
public class UserAuth implements Serializable {

    private static final long serialVersionUID = -6622847960981764597L;

    // 主键ID
    @TableId(type = IdType.AUTO)
    private Long id;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modified;
    // 邮箱
    private String ukEmail;
    // 密码hash
    private String passwordHash;
    // 是否激活
    private Boolean isActivation;
    // 是否删除
    private Boolean isDelete;
}
