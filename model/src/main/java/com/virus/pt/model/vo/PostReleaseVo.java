package com.virus.pt.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 3:54 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class PostReleaseVo {
    @ApiModelProperty(value = "post info 类型", example = "1")
    private Short postInfoType;

    @ApiModelProperty(value = "id", example = "4030964")
    private Long postInfoId;

    @ApiModelProperty(value = "分类id", example = "1", required = true)
    private Long postCategoryId;

    @ApiModelProperty(value = "分辨率", example = "Other", required = true)
    private String resolution;

    @ApiModelProperty(value = "编码", example = "Other", required = true)
    private String codec;

    @ApiModelProperty(value = "媒介", example = "Other", required = true)
    private String medium;

    @ApiModelProperty(value = "音质", example = "Other", required = true)
    private String audio;

    @ApiModelProperty(value = "标题", example = "xxx", required = true)
    private String title;

    @ApiModelProperty(value = "子标题", example = "xxx", required = true)
    private String subtitle;

    @ApiModelProperty(value = "内容", example = "# Other", required = true)
    private String content;

    @ApiModelProperty(value = "系列集合", example = "[{seriesName: 'test'},{seriesName: 'test2'}]", required = true)
    private SeriesVo[] series;
}
