package com.virus.pt.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.virus.pt.common.constant.ApiConst;
import com.virus.pt.common.enums.PostInfoTypeEnum;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.JackJsonUtils;
import com.virus.pt.common.util.JwtUtils;
import com.virus.pt.common.util.PostInfoUtils;
import com.virus.pt.core.service.PostReleaseService;
import com.virus.pt.db.service.*;
import com.virus.pt.model.bo.PostInfoBo;
import com.virus.pt.model.bo.PostReleaseBo;
import com.virus.pt.model.dataobject.*;
import com.virus.pt.model.dto.PostInfoDto;
import com.virus.pt.model.dto.PostQualityDto;
import com.virus.pt.model.vo.PostCountVo;
import com.virus.pt.model.vo.PostInfoVo;
import com.virus.pt.model.vo.PostReleaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
}
