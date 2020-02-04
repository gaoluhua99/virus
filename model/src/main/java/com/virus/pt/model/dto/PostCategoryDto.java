package com.virus.pt.model.dto;

import com.virus.pt.model.dataobject.PostCategoryQuality;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/2/4 3:04 下午
 * @email zzy.main@gmail.com
 */
@ApiModel(description = "文章分类")
@Getter
@Setter
@ToString
public class PostCategoryDto {
    @ApiModelProperty(value = "分类ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "分类名", example = "Movie")
    private String categoryName;

    @ApiModelProperty(value = "质量", example = "")
    private PostCategoryQuality quality;

    @ApiModelProperty(value = "注释", example = "")
    private String remark;
}
