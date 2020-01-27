package com.virus.pt.core.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.BencodeException;
import com.dampcake.bencode.Type;
import com.virus.pt.common.constant.ApiConst;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.*;
import com.virus.pt.core.service.impl.TorrentStorageService;
import com.virus.pt.db.service.TorrentService;
import com.virus.pt.db.service.UserDataService;
import com.virus.pt.db.service.UserInfoService;
import com.virus.pt.model.bo.TorrentBo;
import com.virus.pt.model.dataobject.Torrent;
import com.virus.pt.model.dataobject.UserData;
import com.virus.pt.model.dataobject.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/27 8:15 下午
 * @email zzy.main@gmail.com
 */
@Api(tags = "种子上传、下载、管理")
@RestController
@RequestMapping(value = "${config.virus.url.torrent.value}")
public class TorrentController {
    private static final Logger logger = LoggerFactory.getLogger(TorrentController.class);

    @Autowired
    private TorrentService torrentService;

    @Autowired
    private TorrentStorageService torrentStorageService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserDataService userDataService;

    @Value("${config.virus.name}")
    private String virusName;

    @Value("${config.pestilence.host}")
    private String pestilenceHost;

    @Value("${config.pestilence.port}")
    private String pestilencePort;

    @Value("${config.pestilence.path}")
    private String pestilencePath;

    @Value("${config.pestilence.tracker}")
    private String pestilenceTracker;

    @Value("${config.germ.host}")
    private String germHost;

    @Value("${config.germ.port}")
    private int germPort;

    @Value("${config.germ.path}")
    private String germPath;

    @Value("${config.germ.torrentDetail}")
    private String germTorrentDetail;

    @ApiOperation(value = "用户上传种子")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户", example = ApiConst.TOKEN,
            dataType = "string", paramType = "header", required = true)
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upload(MultipartFile torrent, @ApiIgnore HttpServletRequest request)
            throws TipException {
        Torrent currentTorrent;
        try {
            currentTorrent = TorrentUtils.getTorrentHash(torrent.getBytes());
        } catch (NoSuchAlgorithmException | IOException | BencodeException e) {
            throw new TipException(ResultEnum.TORRENT_TYPE_ERROR);
        }
        Long userId = JwtUtils.getUserIdFromRequest(request);
        // 种子文件名命名规则
        // [ApiName].种子原来名称.torrent
        String torrentName = torrent.getOriginalFilename();
        if (StringUtils.isBlank(torrentName)) {
            throw new TipException(ResultEnum.ARGS_ERROR);
        }
        torrentName = torrentName.replace(".torrent", "").replaceAll("\\[.*]\\.", "");
        String fileName = String.format("[%s].%s.torrent", virusName, torrentName);
        String relativePath = torrentStorageService.getRootLocation().toString() + PathUtils.SPEA +
                DateUtils.getDatePath() +
                fileName;
        try {
            torrentService.saveRollback(
                    TorrentBo.getTorrent(currentTorrent, userId, fileName, relativePath, torrent.getSize()));
        } catch (DuplicateKeyException e) {
            throw new TipException(ResultEnum.SAME_TORRENT_ERROR);
        }
        try {
            FileUtils.writeByteArrayToFile(new File(relativePath), torrent.getBytes());
            logger.info("用户: {}, 存储了种子: {}", userId, currentTorrent);
            return ResponseEntity.ok(JackJsonUtils.json("id", currentTorrent.getId()));
        } catch (IOException e) {
            throw new TipException(ResultEnum.UPLOAD_TORRENT_ERROR);
        }
    }

    @ApiOperation(value = "用户下载种子")
    @GetMapping(value = "${config.virus.url.torrent.download}")
    public ResponseEntity<Resource> download(@PathVariable("tid") String tid, @PathVariable("token") String token,
                                             @ApiIgnore HttpServletRequest request) throws TipException, IOException {
        JwtUtils.verifierJWTToken(VirusUtils.config.getTokenSecret(), token);
        Torrent torrent = torrentService.getOne(new QueryWrapper<Torrent>().eq("id", tid));
        if (torrent == null) {
            throw new TipException(ResultEnum.NO_SUCH_SEED);
        }
        long userId = JwtUtils.getUserId(token);
        // 获取passkey
        UserInfo userInfo = userInfoService.getByUserAuthId(userId);
        if (userInfo == null) {
            throw new TipException(ResultEnum.USER_EMPTY_ERROR);
        }
        UserData userData = userDataService.getByUDId(userInfo.getFkUserDataId());
        if (userData == null) {
            throw new TipException(ResultEnum.USER_EMPTY_ERROR);
        }
        // 替换tracker
        byte[] torrentData;
        try {
            torrentData = FileUtils.readFileToByteArray(new File(torrent.getFilePath()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Bencode bencode = new Bencode(true);
        Map<String, Object> torrentMap = bencode.decode(torrentData, Type.DICTIONARY);
        torrentMap.remove("announce-list");
        if (pestilencePath.equals("/")) {
            torrentMap.put("announce", pestilenceHost + ":" + pestilencePort + pestilenceTracker
                    + "?passkey=" + userData.getUkPasskey());
        } else {
            torrentMap.put("announce", pestilenceHost + ":" + pestilencePort + pestilencePath + pestilenceTracker
                    + "?passkey=" + userData.getUkPasskey());
        }
        if (germPath.equals("/")) {
            torrentMap.put("comment", germHost + ":" + germPort + germTorrentDetail + "?id=" + torrent.getId());
        } else {
            torrentMap.put("comment", germHost + ":" + germPort + germPath + germTorrentDetail + "?id=" + torrent.getId());
        }
        ByteArrayResource resource = new ByteArrayResource(bencode.encode(torrentMap));
        // 实例化MIME
        MediaType mediaType = MediaType.parseMediaType("application/x-bittorrent");
        /*
         * 构造响应的头
         */
        HttpHeaders headers = new HttpHeaders();
        // 下载之后需要在请求头中放置文件名，该文件名按照ISO_8859_1编码。
        String filenames = new String(
                URLEncoder.encode(torrent.getFileName(), "UTF-8").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        headers.setContentDispositionFormData("attachment", filenames);
        headers.setContentType(mediaType);
        /*
         * 返还资源
         */
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.getInputStream().available())
                .body(resource);
    }
}
