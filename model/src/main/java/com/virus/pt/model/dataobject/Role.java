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
 * @date 2020/1/13 2:11 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
@TableName(value = "t_role")
public class Role implements Serializable {
    private static final long serialVersionUID = -7128446121120594267L;
    @TableId(type = IdType.AUTO)
    private Long id;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modified;
    private String roleName;
    private String remark;
    private Boolean isDelete;
}
