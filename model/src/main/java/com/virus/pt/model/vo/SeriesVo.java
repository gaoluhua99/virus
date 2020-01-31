package com.virus.pt.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 3:58 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class SeriesVo {
    private String seriesName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String remark;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long[] torrents;
}
