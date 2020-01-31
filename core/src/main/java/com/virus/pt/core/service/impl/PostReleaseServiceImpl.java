package com.virus.pt.core.service.impl;

import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.core.service.PostReleaseService;
import com.virus.pt.db.service.*;
import com.virus.pt.model.bo.PostContentBo;
import com.virus.pt.model.bo.PostReleaseBo;
import com.virus.pt.model.bo.SeriesBo;
import com.virus.pt.model.dataobject.*;
import com.virus.pt.model.vo.PostReleaseVo;
import com.virus.pt.model.vo.SeriesVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/31 5:35 下午
 * @email zzy.main@gmail.com
 */
@Service
public class PostReleaseServiceImpl implements PostReleaseService {
    private static final Logger logger = LoggerFactory.getLogger(PostReleaseServiceImpl.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserTeamService userTeamService;

    @Autowired
    private PostContentService postContentService;

    @Autowired
    private PostService postService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private SeriesTorrentService seriesTorrentService;

    @Override
    public boolean saveRollback(long userId, String qualityJson, PostReleaseVo postReleaseVo) throws TipException {
        Post post = PostReleaseBo.getPost(postReleaseVo, userId, qualityJson);
        UserInfo userInfo = userInfoService.getByUserAuthId(userId);
        post.setFkUserInfoId(userInfo.getId());
        UserTeam userTeam = userTeamService.getByUserAuthId(userId);
        if (userTeam != null) {
            post.setFkUserTeamId(userTeam.getId());
        }
        PostContent postContent = PostContentBo.getPostContent(postReleaseVo.getContent());
        if (postContentService.saveRollback(postContent)) {
            post.setFkPostContentId(postContent.getId());
            if (postService.saveRollback(post)) {
                boolean save = true;
                // 保存所有系列
                for (SeriesVo seriesVo : postReleaseVo.getSeries()) {
                    Series series;
                    if (userTeam != null) {
                        series = SeriesBo.getSeries(seriesVo, userId, userTeam.getId(), post.getId());
                    } else {
                        series = SeriesBo.getSeries(seriesVo, userId, null, post.getId());
                    }
                    if (seriesService.saveRollback(series)) {
                        for (long tid : seriesVo.getTorrents()) {
                            // 保存系列种子关系
                            SeriesTorrent seriesTorrent = new SeriesTorrent();
                            seriesTorrent.setFkSeriesId(series.getId());
                            seriesTorrent.setFkTorrentId(tid);
                            if (!seriesTorrentService.saveRollback(seriesTorrent)) {
                                throw new TipException(ResultEnum.RELEASE_TORRENT_ERROR);
                            }
                        }
                    } else {
                        // 系列保存失败直接抛出异常
                        throw new TipException(ResultEnum.RELEASE_SERIES_ERROR);
                    }
                }
                logger.info("ID: {}, name: {}, 发布了新文章, id: {}, 标题: {}",
                        userId, userInfo.getUkUsername(), post.getId(), post.getTitle());
                return true;
            }
            throw new TipException(ResultEnum.RELEASE_ERROR);
        }
        throw new TipException(ResultEnum.RELEASE_CONTENT_ERROR);
    }
}
