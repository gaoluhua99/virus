package com.virus.pt.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 7:34 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class SeriesDto {
    private Long id;
    private Long userAuthId;
    private Long userTeamId;
    private String seriesName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String remark;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TorrentDto> torrentList;

    private Long create;
    private Long modify;
}
