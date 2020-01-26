package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/13 2:04 下午
 * @email zzy.main@gmail.com
 */
@Data
@TableName(value = "t_user_data")
public class UserData implements Serializable {
    private static final long serialVersionUID = 7892068697488077053L;
    @TableId(type = IdType.AUTO)
    private Long id;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modified;
    private String ukPasskey;
    private Long uploaded;
    private Long downloaded;
    private Short userStatus;
    private Boolean isDelete;
}
