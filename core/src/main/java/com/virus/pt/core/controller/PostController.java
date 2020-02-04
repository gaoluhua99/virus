package com.virus.pt.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.virus.pt.common.constant.ApiConst;
import com.virus.pt.common.enums.PostInfoTypeEnum;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.enums.TorrentDiscountEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.JackJsonUtils;
import com.virus.pt.common.util.JwtUtils;
import com.virus.pt.common.util.PostInfoUtils;
import com.virus.pt.core.service.PostReleaseService;
import com.virus.pt.db.service.*;
import com.virus.pt.model.bo.*;
import com.virus.pt.model.dataobject.*;
import com.virus.pt.model.dto.PostDto;
import com.virus.pt.model.dto.PostInfoDto;
import com.virus.pt.model.dto.PostQualityDto;
import com.virus.pt.model.dto.SeriesDto;
import com.virus.pt.model.vo.PostCountVo;
import com.virus.pt.model.vo.PostInfoVo;
import com.virus.pt.model.vo.PostListVo;
import com.virus.pt.model.vo.PostReleaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 12:58 下午
 * @email zzy.main@gmail.com
 */
@Api(tags = "发布文章、获取文章数、获取文章、根据imdbId或豆瓣id检索信息")
@RestController
@RequestMapping(value = "${config.virus.url.post.value}")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private PostInfoService postInfoService;

    @Autowired
    private PostReleaseService postReleaseService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private SeriesTorrentService seriesTorrentService;

    @Autowired
    private PeerService peerService;

    @Autowired
    private TorrentStatusService torrentStatusService;

    @Autowired
    private TorrentDiscountService torrentDiscountService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PostContentService postContentService;

    @ApiOperation(value = "获取文章数")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @PostMapping(value = "${config.virus.url.post.count}")
    public ResponseEntity<String> count(@RequestBody PostCountVo postCountVo, @ApiIgnore HttpServletRequest request) {
        return ResponseEntity.ok(JackJsonUtils.json("count",
                this.postService.count(postCountVo.getCategoryName(), postCountVo.getIsWait())));
    }

    @ApiOperation(value = "根据imdbId或豆瓣id检索信息")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @PostMapping(value = "${config.virus.url.post.info}")
    public ResponseEntity<PostInfoDto> info(@RequestBody PostInfoVo postInfoVo,
                                            @ApiIgnore HttpServletRequest request) throws TipException, IOException {
        // 先查询数据库，没有再从网络获取
        PostInfo postInfo = postInfoService.getByInfoId(postInfoVo.getType(), postInfoVo.getId());
        if (postInfo != null) {
            return ResponseEntity.ok(PostInfoUtils.getPostInfoDto(postInfo));
        }
        // 从各API获取数据
        try {
            JsonNode jsonNode = PostInfoUtils.get(postInfoVo)
                    .doOnError(throwable -> logger.error("获取信息错误, info: {}", postInfoVo))
                    .block();
            if (jsonNode != null) {
                PostInfoDto postInfoDto = PostInfoUtils.getPostInfoDto(postInfoVo.getType(), postInfoVo.getId(), jsonNode);
                // 存储到数据库
                postInfoService.save(PostInfoBo.getPostInfo(postInfoVo.getType(), postInfoDto, jsonNode.toString()));
                return ResponseEntity.ok(postInfoDto);
            }
            throw new TipException(ResultEnum.ARGS_ERROR);
        } catch (Exception e) {
            if (postInfoVo.getType() == PostInfoTypeEnum.IMDB.getInfoType()) {
                throw new TipException(ResultEnum.IMDB_NOT_FOUND);
            } else {
                throw new TipException(ResultEnum.DOUBAN_NOT_FOUND);
            }
        }
    }

    @ApiOperation(value = "发布文章")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @PostMapping(value = "")
    public ResponseEntity<?> release(@RequestBody PostReleaseVo postReleaseVo, @ApiIgnore HttpServletRequest request)
            throws TipException, JsonProcessingException {
        long userId = JwtUtils.getUserIdFromRequest(request);
        PostQualityDto postQualityByReleaseVo = PostReleaseBo.getPostQuality(postReleaseVo);
        String qualityJson = JackJsonUtils.json(postQualityByReleaseVo);
        if (postReleaseService.saveRollback(userId, qualityJson, postReleaseVo)) {
            return ResponseEntity.ok().build();
        }
        throw new TipException(ResultEnum.ERROR);
    }

    @ApiOperation(value = "获取所有文章")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @PostMapping(value = "${config.virus.url.post.list}")
    public ResponseEntity<List<PostDto>> list(@RequestBody PostListVo postListVo,
                                              @ApiIgnore HttpServletRequest request) throws TipException, IOException {
        if (postListVo.getIsWait() == null) {
            throw new TipException(ResultEnum.ARGS_ERROR);
        }
        if (postListVo.getPageSize() == null || postListVo.getPageSize() > 50) {
            throw new TipException(ResultEnum.GET_POST_LEN_ERROR);
        }
        List<Post> postList;
        if (StringUtils.isBlank(postListVo.getCategoryName()) || postListVo.getIsWait()) {
            if (StringUtils.isBlank(postListVo.getSortKey())) {
                postList = postService.getPostPage(
                        postListVo.getPageIndex(), postListVo.getPageSize(), postListVo.getIsWait()).getRecords();
            } else {
                switch (postListVo.getSortKey()) {
                    case "created":
                        postList = postService.getPostPageOrderByCreated(
                                postListVo.getPageIndex(), postListVo.getPageSize(), postListVo.getDesc(), postListVo.getIsWait()).getRecords();
                        break;
                    case "title":
                        postList = postService.getPostPageOrderByTitle(
                                postListVo.getPageIndex(), postListVo.getPageSize(), postListVo.getDesc(), postListVo.getIsWait()).getRecords();
                        break;
                    case "uid":
                        postList = postService.getPostPageOrderByUid(
                                postListVo.getPageIndex(), postListVo.getPageSize(), postListVo.getDesc(), postListVo.getIsWait()).getRecords();
                        break;
                    case "size":
                        postList = postService.getPostPageOrderBySize(
                                postListVo.getPageIndex(), postListVo.getPageSize(), postListVo.getDesc(), postListVo.getIsWait()).getRecords();
                        break;
                    default:
                        throw new TipException(ResultEnum.CATEGORY_ERROR);
                }
            }
        } else {
            if (StringUtils.isBlank(postListVo.getSortKey())) {
                postList = postService.getPostPageByCategoryName(
                        postListVo.getPageIndex(), postListVo.getPageSize(), postListVo.getCategoryName(), postListVo.getIsWait()).getRecords();
            } else {
                switch (postListVo.getSortKey()) {
                    case "created":
                        postList = postService.getPostPageByCategoryNameOrderByCreated(
                                postListVo.getPageIndex(), postListVo.getPageSize(), postListVo.getCategoryName(),
                                postListVo.getDesc(), postListVo.getIsWait()).getRecords();
                        break;
                    case "title":
                        postList = postService.getPostPageByCategoryNameOrderByTitle(
                                postListVo.getPageIndex(), postListVo.getPageSize(), postListVo.getCategoryName(),
                                postListVo.getDesc(), postListVo.getIsWait()).getRecords();
                        break;
                    case "uid":
                        postList = postService.getPostPageByCategoryNameOrderByUid(
                                postListVo.getPageIndex(), postListVo.getPageSize(), postListVo.getCategoryName(),
                                postListVo.getDesc(), postListVo.getIsWait()).getRecords();
                        break;
                    case "size":
                        postList = postService.getPostPageByCategoryNameOrderBySize(
                                postListVo.getPageIndex(), postListVo.getPageSize(), postListVo.getCategoryName(),
                                postListVo.getDesc(), postListVo.getIsWait()).getRecords();
                        break;
                    default:
                        throw new TipException(ResultEnum.CATEGORY_ERROR);
                }
            }
        }
        List<PostDto> postDtoList = new ArrayList<>();
        for (Post post : postList) {
            List<SeriesDto> seriesDtoList = new ArrayList<>();
            if (!postListVo.getIsWait()) {
                // 获取文章对应的所有系列
                List<Series> seriesList = seriesService.getListByPostId(post.getId());
                genSeriesDtoList(seriesDtoList, seriesList);
            }
            String username = userInfoService.getByUserAuthId(post.getFkUserAuthId()).getUkUsername();
            PostInfo douban = null, imdb = null;
            if (post.getDoubanId() != null) {
                douban = postInfoService.getDouban(post.getDoubanId());
            }
            if (post.getImdbId() != null) {
                imdb = postInfoService.getByInfoId((short) PostInfoTypeEnum.IMDB.getInfoType(), post.getImdbId());
            }
            postDtoList.add(PostBo.getPostDto(post, username, seriesDtoList,
                    PostInfoUtils.getPostInfoDto(douban), PostInfoUtils.getPostInfoDto(imdb)));
        }
        return ResponseEntity.ok(postDtoList);
    }

    private void genSeriesDtoList(List<SeriesDto> seriesDtoList, List<Series> seriesList) {
        seriesList.forEach(series -> {
            // 获取系列下所有种子
            List<Torrent> torrentList = seriesTorrentService.getListBySeriesId(series.getId());
            Map<Long, TorrentDiscount> discountMap = new HashMap<>();
            Map<Long, Long> uploadMap = new HashMap<>();
            Map<Long, Long> downloadMap = new HashMap<>();
            Map<Long, Long> completeMap = new HashMap<>();
            // 获取种子的优惠信息等
            torrentList.forEach(torrent -> {
                long uploadCount = peerService.getSeedingCount(torrent.getId());
                long downloadCount = peerService.getDownloadingCount(torrent.getId());
                uploadMap.put(torrent.getId(), uploadCount);
                downloadMap.put(torrent.getId(), downloadCount);
                completeMap.put(torrent.getId(), torrentStatusService.countByTid(torrent.getId()));
                TorrentDiscount discount = torrentDiscountService.get(torrent.getFkTorrentDiscountId());
                if (discount.getLimitTime() == 0
                        || discount.getCreated().getTime() + discount.getLimitTime() > System.currentTimeMillis()) {
                    discountMap.put(torrent.getId(), discount);
                } else {
                    discountMap.put(torrent.getId(), TorrentDiscountEnum.toDiscount(TorrentDiscountEnum.NORMAL));
                }
            });
            seriesDtoList.add(
                    SeriesBo.getSeriesDto(
                            series, TorrentBo.getTorrentDtoList(torrentList, discountMap, uploadMap, completeMap, downloadMap)));
        });
    }

    @ApiOperation(value = "根据文章ID获取文章")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @GetMapping(value = "${config.virus.url.post.id}")
    public ResponseEntity<PostDto> get(@PathVariable("id") Long id) throws TipException, IOException {
        Post post = postService.get(id);
        if (post != null) {
            List<SeriesDto> seriesDtoList = new ArrayList<>();
            // 获取文章对应的所有系列
            List<Series> seriesList = seriesService.getListByPostId(post.getId());
            genSeriesDtoList(seriesDtoList, seriesList);
            PostInfo douban = null, imdb = null;
            if (post.getDoubanId() != null) {
                douban = postInfoService.getDouban(post.getDoubanId());
            }
            if (post.getImdbId() != null) {
                imdb = postInfoService.getByInfoId((short) PostInfoTypeEnum.IMDB.getInfoType(), post.getImdbId());
            }
            PostDto postDto = PostBo.getPostDto(post,
                    userInfoService.getByUserAuthId(post.getFkUserAuthId()).getUkUsername(), seriesDtoList,
                    PostInfoUtils.getPostInfoDto(douban), PostInfoUtils.getPostInfoDto(imdb));
            PostContent postContent = postContentService.get(post.getFkPostContentId());
            if (postContent != null) {
                postDto.setContent(postContent.getContent());
            }
            return ResponseEntity.ok(postDto);
        }
        throw new TipException(ResultEnum.POST_EMPTY_ERROR);
    }
}
