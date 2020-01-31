package com.virus.pt.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 4:21 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class PostQualityDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String resolution;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String codec;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String medium;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String audio;
}
