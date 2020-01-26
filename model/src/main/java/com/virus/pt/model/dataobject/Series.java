package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/26 11:57 上午
 * @email zzy.main@gmail.com
 */
@Data
@TableName(value = "t_series")
public class Series implements Serializable {
    private static final long serialVersionUID = -112980907125926060L;
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
    private Long fkUserTeamId;
    private Long fkPostId;
    private String seriesName;
    private String remark;
}
