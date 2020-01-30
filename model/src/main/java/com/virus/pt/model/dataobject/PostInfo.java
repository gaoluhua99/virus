package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/26 11:53 上午
 * @email zzy.main@gmail.com
 */
@Data
@TableName(value = "t_post_info")
public class PostInfo implements Serializable {
    private static final long serialVersionUID = -31597982614399745L;
    @TableId(type = IdType.AUTO)
    private Long id;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modified;
    private Boolean isDelete;
    private Short infoType;
    private Long ukInfoId;
    private BigDecimal ratingAverage;
    private Long ratingNumRaters;
    private String summary;
    private String posterUrl;
    private String content;
}
