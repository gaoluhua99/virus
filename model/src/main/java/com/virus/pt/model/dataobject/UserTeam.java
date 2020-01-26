package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/26 12:03 下午
 * @email zzy.main@gmail.com
 */
@Data
@TableName(value = "t_user_team")
public class UserTeam implements Serializable {
    private static final long serialVersionUID = 5424804593309764272L;
    @TableId(type = IdType.AUTO)
    private Long id;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modified;
    private Boolean isDelete;
    private Long fkUserAuthId;
    private String ukTeamName;
    private String remark;
}
