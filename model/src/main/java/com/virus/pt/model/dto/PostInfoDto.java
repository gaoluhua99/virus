package com.virus.pt.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 8:30 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class PostInfoDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float ratingAverage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer ratingNumRaters;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String year;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String language;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String country;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String summary;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String poster;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String writer;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String actors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String released;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String runtime;
}
