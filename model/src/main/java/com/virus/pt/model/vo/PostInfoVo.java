package com.virus.pt.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 8:10 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class PostInfoVo {
    @ApiModelProperty(value = "类型", example = "1", required = false)
    private Short type;

    @ApiModelProperty(value = "id", example = "26931786", required = true)
    private Long id;
}
