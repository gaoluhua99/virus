package com.virus.pt.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 6:35 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class PostListVo {
    @ApiModelProperty(value = "文章页码", example = "1", required = true)
    private Integer pageIndex;

    @ApiModelProperty(value = "每页文章显示数，最大50", example = "10", required = true)
    private Integer pageSize;

    @ApiModelProperty(value = "分类名", example = "Movie", required = true)
    private String categoryName;

    @ApiModelProperty(value = "排序Key", example = "title", required = true)
    private String sortKey;

    @ApiModelProperty(value = "desc", example = "true", required = true)
    private Boolean desc;

    @ApiModelProperty(value = "是否候选", example = "false", required = true)
    private Boolean isWait;
}
