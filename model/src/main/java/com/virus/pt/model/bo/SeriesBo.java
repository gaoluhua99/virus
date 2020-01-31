package com.virus.pt.model.bo;

import com.virus.pt.model.dataobject.Series;
import com.virus.pt.model.vo.SeriesVo;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 5:16 下午
 * @email zzy.main@gmail.com
 */
public class SeriesBo {
    public static Series getSeries(SeriesVo seriesVo, long userId, Long userTeamId, Long postId) {
        Series series = new Series();
        series.setFkUserAuthId(userId);
        if (userTeamId != null) {
            series.setFkUserTeamId(userTeamId);
        }
        series.setFkPostId(postId);
        series.setSeriesName(seriesVo.getSeriesName());
        series.setRemark(seriesVo.getRemark());
        return series;
    }
}
