package com.virus.pt.core.controller;

import com.virus.pt.common.constant.ApiConst;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.*;
import com.virus.pt.core.service.UserService;
import com.virus.pt.core.service.impl.AvatarStorageService;
import com.virus.pt.db.service.UserAuthService;
import com.virus.pt.db.service.UserInfoService;
import com.virus.pt.model.dataobject.UserInfo;
import com.virus.pt.model.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/22 11:50 上午
 * @email zzy.main@gmail.com
 */
@Api(tags = "用户获取信息、签到、上传头像")
@RestController
@RequestMapping(value = "${config.virus.url.user.value}")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserService userService;

    @Autowired
    private AvatarStorageService avatarStorageService;

    @Value("${config.virus.file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${config.virus.file.uploadFolder}")
    private String uploadFolder;

    @Value("${config.virus.file.avatarFolder}")
    private String avatarFolder;

    @Value("${config.virus.host}")
    private String virusHost;

    @Value("${config.virus.port}")
    private int virusPost;

    @Value("${config.virus.path}")
    private String virusPath;

    @ApiOperation(value = "根据用户名判断用户是否存在")
    @GetMapping(value = "${config.virus.url.user.existByUsername}")
    public ResponseEntity<Boolean> getUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userInfoService.existByUsername(username));
    }

    @ApiOperation(value = "根据邮箱判断用户是否存在")
    @GetMapping(value = "${config.virus.url.user.existByEmail}")
    public ResponseEntity<Boolean> getUserByEmail(@PathVariable("email") String email) throws TipException {
        return ResponseEntity.ok(userAuthService.existByEmail(email));
    }

    @ApiOperation(value = "根据用户token获取用户信息")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @GetMapping(value = "")
    public ResponseEntity<UserDto> get(@ApiIgnore HttpServletRequest request) throws TipException {
        Long userAuthId = JwtUtils.getUserIdFromRequest(request);
        UserDto userDTO = userService.getByUserAuthId(userAuthId);
//        userDTO.setIsSigned(signRecordService.isSigned(userId));
        return ResponseEntity.ok(userDTO);
    }

    @ApiOperation(value = "用户上传头像")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @PostMapping(value = "${config.virus.url.user.avatar}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> avatarUpload(MultipartFile image, @ApiIgnore HttpServletRequest request)
            throws TipException, IOException {
        // 检查是不是图片
        if (image == null || !ImageUtils.isImage(image.getInputStream()) || image.getContentType() == null) {
            throw new TipException(ResultEnum.IMAGE_TYPE_ERROR);
        }
        // 文件名与数据库一一对应
        // 根据当前时间戳的hash生成文件名
        String fileName = DigestUtils.md5Hex(String.valueOf(System.currentTimeMillis()));
        String fileType = image.getContentType().replace("image/", "");
        String relativePath = avatarStorageService.getRootLocation().toString() + PathUtils.SPEA
                + DateUtils.getDatePath()
                + fileName + "." + fileType;
        Long userId = JwtUtils.getUserIdFromRequest(request);
        // 存储到本地
        if (ImageUtils.saveImage(image.getInputStream(), fileType, new File(relativePath))) {
            // 插入数据库
            // 存储相对位置
//            attachService.save(AttachBo.getAttach(
//                    userId, image.getOriginalFilename(), image.getSize(), relativePath, FileTypeEnum.IMAGE.getName()));
            String url = virusHost + ":" + virusPost + virusPath + PathUtils.SPEA_URL +
                    staticAccessPath + PathUtils.SPEA_URL +
                    relativePath.replaceFirst(avatarStorageService.getRootLocation().toString(), avatarFolder);
            // 更新用户信息
            UserInfo userInfo = new UserInfo();
            userInfo.setFkUserAuthId(userId);
            userInfo.setAvatarUrl(url);
            boolean isUpdate = userInfoService.updateByUserAuthId(userInfo);
            logger.info("uid: {}, 上传了新头像: {} 结果: {}", userId, url, isUpdate);
            if (!isUpdate) {
                throw new TipException(ResultEnum.DB_UPDATE_ERROR);
            }
            return ResponseEntity.ok(JackJsonUtils.json("url", url));
        } else {
            throw new TipException(ResultEnum.UPLOAD_IMAGE_ERROR);
        }
    }
}
