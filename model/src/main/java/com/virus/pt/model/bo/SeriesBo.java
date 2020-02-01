package com.virus.pt.model.bo;

import com.virus.pt.model.dataobject.Series;
import com.virus.pt.model.dto.SeriesDto;
import com.virus.pt.model.dto.TorrentDto;
import com.virus.pt.model.util.BeanUtils;
import com.virus.pt.model.vo.SeriesVo;

import java.util.List;

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

    public static SeriesDto getSeriesDto(Series series, List<TorrentDto> torrentDtoList) {
        SeriesDto seriesDto = new SeriesDto();
        BeanUtils.copyFieldToBean(series, seriesDto);
        seriesDto.setUserAuthId(series.getFkUserAuthId());
        if (series.getFkUserTeamId() != null) {
            seriesDto.setUserTeamId(series.getFkUserTeamId());
        }
        seriesDto.setCreate(series.getCreated().getTime());
        seriesDto.setModify(series.getModified().getTime());
        if (torrentDtoList.size() > 0) {
            seriesDto.setTorrentList(torrentDtoList);
        }
        return seriesDto;
    }
}
