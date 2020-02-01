package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author intent
 * @date 2019/8/1 16:18
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Data
@TableName(value = "t_torrent")
public class Torrent implements Serializable {

    private static final long serialVersionUID = -3617303636715796178L;
    @TableId(type = IdType.AUTO)
    private Long id;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date created;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modified;
    private Long fkUserAuthId;
    private Long fkTorrentDiscountId;
    private byte[] ukInfoHash;
    private String fileName;
    private Long fileSize;
    private String filePath;
    private Long torrentSize;
    private Long torrentCount;
    private Boolean isDelete;
}
