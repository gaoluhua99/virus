package com.virus.pt.model.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author intent
 * @version 1.0
 * @date 2020/2/4 3:56 下午
 * @email zzy.main@gmail.com
 */
@Getter
@Setter
@ToString
public class PostCategoryQuality {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private QualityItem resolution;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private QualityItem codec;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private QualityItem medium;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private QualityItem audio;
}


@Getter
@Setter
@ToString
class QualityItem {
    private String name;
    private List value;
}