package com.virus.pt.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 7:32 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class PostDto {
    private Long id;
    private Long userAuthId;
    private String username;
    private String categoryName;
    private List<SeriesDto> seriesList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PostInfoDto douban;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PostInfoDto imdb;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer teamId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String teamName;

    private Integer pin;
    private String title;
    private String subtitle;
    private Integer hot;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;

    private Long create;
    private Long modify;
}
