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
 * @date 2020/1/13 2:14 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
@TableName(value = "t_torrent_status")
public class TorrentStatus implements Serializable {
    private static final long serialVersionUID = -9045597729849786913L;
    @TableId(type = IdType.AUTO)
    private Long id;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modified;
    private Long fkUserDataId;
    private Long fkTorrentId;
    private Boolean torrentStatus;
    private String ip;
    private String clientName;
    private Boolean isDelete;
}
