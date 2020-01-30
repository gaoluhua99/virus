package com.virus.pt.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 1:20 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class PostCountVo {
    @ApiModelProperty(value = "分类名", example = "Movie", required = false)
    private String categoryName;

    @ApiModelProperty(value = "是否候选", example = "false", required = true)
    private Boolean isWait;
}
