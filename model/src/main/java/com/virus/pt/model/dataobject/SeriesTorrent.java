package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/26 11:58 上午
 * @email zzy.main@gmail.com
 */
@Data
@TableName(value = "t_series_torrent")
public class SeriesTorrent implements Serializable {
    private static final long serialVersionUID = 498688772948020001L;
    @TableId(type = IdType.AUTO)
    private Long id;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modified;
    private Boolean isDelete;
    private Long fkSeriesId;
    private Long fkTorrentId;
}
