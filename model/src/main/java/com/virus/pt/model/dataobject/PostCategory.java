package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/26 11:47 上午
 * @email zzy.main@gmail.com
 */
@Data
@TableName(value = "t_post_category")
public class PostCategory implements Serializable {
    private static final long serialVersionUID = 1560342659722725633L;
    @TableId(type = IdType.AUTO)
    private Long id;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modified;
    private String categoryName;
    private String quality;
    private String remark;
    private Boolean isDelete;
}
