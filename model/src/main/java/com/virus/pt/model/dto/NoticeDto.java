package com.virus.pt.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 12:14 下午
 * @email zzy.main@gmail.com
 */
@ApiModel(description = "公告")
@Getter
@Setter
public class NoticeDto {
    @ApiModelProperty(value = "公告标题", example = "公告标题")
    private String title;

    @ApiModelProperty(value = "公告内容", example = "公告内容")
    private String message;

    @ApiModelProperty(value = "是否展开", example = "true")
    private Boolean isOpen;

    @ApiModelProperty(value = "公告创建时间", example = "1561004995625")
    private Long create;

    @ApiModelProperty(value = "公告更新时间", example = "1561518515485")
    private Long modify;
}
