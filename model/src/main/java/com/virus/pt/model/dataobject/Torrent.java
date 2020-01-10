package com.virus.pt.model.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author intent
 * @date 2019/8/1 16:18
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Getter
@Setter
@ToString
@TableName(value = "t_torrent")
public class Torrent implements Serializable {

    private static final long serialVersionUID = -3617303636715796178L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private String fileName;
    private Long fileSize;
    private String filePath;
    private byte[] infoHash;
    private Integer discountId;
    private Long size;
    private Integer torrentCount;
    @TableField(fill = FieldFill.INSERT)
    private Long created;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long modify;
}
