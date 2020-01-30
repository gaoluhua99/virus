package com.virus.pt.core.controller;

import com.virus.pt.common.constant.ApiConst;
import com.virus.pt.common.util.JackJsonUtils;
import com.virus.pt.db.service.PostService;
import com.virus.pt.model.vo.PostCountVo;
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

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/30 12:58 下午
 * @email zzy.main@gmail.com
 */
@Api(tags = "发布文字、获取文章数、获取文章、根据imdbId或豆瓣id检索信息")
@RestController
@RequestMapping(value = "${config.virus.url.post.value}")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @ApiOperation(value = "获取文章数")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @PostMapping(value = "${config.virus.url.post.count}")
    public ResponseEntity<String> count(@RequestBody PostCountVo postCountVo, @ApiIgnore HttpServletRequest request) {
        return ResponseEntity.ok(JackJsonUtils.json("count",
                this.postService.count(postCountVo.getCategoryName(), postCountVo.getIsWait())));
    }
}
