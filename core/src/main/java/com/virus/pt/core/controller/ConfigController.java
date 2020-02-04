package com.virus.pt.core.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.virus.pt.common.constant.ApiConst;
import com.virus.pt.common.enums.UserDataEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.JackJsonUtils;
import com.virus.pt.db.service.*;
import com.virus.pt.model.bo.ConfigBo;
import com.virus.pt.model.bo.NoticeBo;
import com.virus.pt.model.dataobject.PostCategory;
import com.virus.pt.model.dataobject.PostCategoryQuality;
import com.virus.pt.model.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "获取配置、更新配置、获取公告、获取站点信息等")
@RestController
@RequestMapping("${config.virus.url.config.value}")
public class ConfigController {
    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    private ConfigService configService;

    @Autowired
    private UserLevelService userLevelService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PostCategoryService postCategoryService;

    @Value("${config.virus.notice.len}")
    private long noticeLen;

    /**
     * 获取配置参数
     *
     * @return {@link com.virus.pt.model.dto.ConfigDto}
     */
    @ApiOperation(value = "获取配置参数")
    @GetMapping(value = "")
    public ResponseEntity<ConfigDto> getConfig() throws TipException {
        return ResponseEntity.ok(ConfigBo.getConfigDto(configService.get()));
    }

    /**
     * 已登录的用户获取配置
     *
     * @return {@link com.virus.pt.model.dto.ConfigUserDto}
     */
    @ApiOperation(value = "已登录的用户获取配置")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @GetMapping(value = "${config.virus.url.config.user}")
    public ResponseEntity<ConfigUserDto> getConfigUser() throws TipException, IOException {
        List<PostCategory> postCategoryList = postCategoryService.getAll();
        List<PostCategoryDto> postCategoryDtoList = new ArrayList<>();
        for (PostCategory postCategory : postCategoryList) {
            PostCategoryDto postCategoryDto = new PostCategoryDto();
            postCategoryDto.setId(postCategory.getId());
            postCategoryDto.setCategoryName(postCategory.getCategoryName());
            PostCategoryQuality categoryQuality = JackJsonUtils.parseObject(
                    postCategory.getQuality(), PostCategoryQuality.class);
            postCategoryDto.setQuality(categoryQuality);
            postCategoryDto.setRemark(postCategory.getRemark());
            postCategoryDtoList.add(postCategoryDto);
        }
        return ResponseEntity.ok(
                ConfigBo.getConfigUserDto(
                        configService.get(), userLevelService.getAll(), postCategoryDtoList));
    }

    /**
     * 已登录用户获取公告
     *
     * @return {@link java.util.List<com.virus.pt.model.dto.NoticeDto>}
     */
    @ApiOperation(value = "已登录用户获取公告")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @GetMapping(value = "${config.virus.url.config.notice}")
    public ResponseEntity<List<NoticeDto>> getNotice() {
        return ResponseEntity.ok(NoticeBo.getNoticeDtoList(noticeService.getAll(0, noticeLen).getRecords()));
    }

    /**
     * 已登录用户获取站点信息
     *
     * @return {@link com.virus.pt.model.dto.SiteInfoDto}
     */
    @ApiOperation(value = "已登录用户获取站点信息")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @GetMapping(value = "${config.virus.url.config.siteInfo}")
    public ResponseEntity<SiteInfoDto> getSiteInfo() {
        return ResponseEntity.ok(ConfigBo.getSiteInfoDto(userAuthService.countTotal(),
                userDataService.countByStatus((short) UserDataEnum.WARNING.getCode()),
                userDataService.countByStatus((short) UserDataEnum.BAN.getCode()),
                userAuthService.countNotActivation(),
                userInfoService.countBySex(true), userInfoService.countBySex(false)));
    }

    // TODO 管理员更新配置
}
