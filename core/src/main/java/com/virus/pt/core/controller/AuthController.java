package com.virus.pt.core.controller;

import com.virus.pt.common.enums.FileTypeEnum;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.*;
import com.virus.pt.core.service.UserService;
import com.virus.pt.core.service.impl.CaptchaStorageService;
import com.virus.pt.core.util.CaptchaUtils;
import com.virus.pt.db.service.CaptchaService;
import com.virus.pt.db.service.UserAuthService;
import com.virus.pt.mail.service.MailService;
import com.virus.pt.model.dto.UserDto;
import com.virus.pt.model.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @author intent
 * @version 1.0
 * @date 2020/1/21 1:31 下午
 * @email zzy.main@gmail.com
 */
@Api(tags = "登录、注册、登出、获取验证码、激活用户、修改密码")
@RestController
@RequestMapping(value = "${config.virus.url.auth.value}")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private CaptchaStorageService captchaStorageService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthService userAuthService;

    @Value("${config.virus.file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${config.virus.file.uploadFolder}")
    private String uploadFolder;

    @Value("${config.virus.file.captchaFolder}")
    private String captchaFolder;

    @Value("${config.germ.host}")
    private String germHost;

    @Value("${config.germ.port}")
    private int germPort;

    @Value("${config.germ.path}")
    private String germPath;

    @Value("${config.virus.host}")
    private String virusHost;

    @Value("${config.virus.port}")
    private int virusPost;

    @Value("${config.virus.path}")
    private String virusPath;

    /**
     * 获取验证码
     *
     * @param request request
     * @return {@link ResultEnum#SUCCESS}
     */
    @ApiOperation(value = "获取验证码")
    @GetMapping(value = "${config.virus.url.auth.captcha}")
    public ResponseEntity<String> captcha(@ApiIgnore HttpServletRequest request)
            throws TipException {
        // 时间戳
        long millis = System.currentTimeMillis();
        // 生成验证码并返回验证码值
        String captchaCode = CaptchaUtils.genCaptcha(captchaStorageService, millis);
        // 放入redis
        captchaService.saveCaptcha(IPUtils.getIpAddr(request), captchaCode);
        // 验证码访问url http://localhost:7290/virus/file/captcha/1563871102973.jpg
        String captchaUrl = virusHost + ":" + virusPost + virusPath + PathUtils.SPEA_URL +
                staticAccessPath + PathUtils.SPEA_URL +
                captchaFolder + PathUtils.SPEA_URL + millis + "." + FileTypeEnum.CAPTCHA_IMAGE.getName();
        logger.info("ip: {} 获取了验证码, millis: {}, code: {}, url: {}",
                IPUtils.getIpAddr(request), millis, captchaCode, captchaUrl);
        return ResponseEntity.ok(JackJsonUtils.json("url", captchaUrl));
    }

    /**
     * 用户注册
     * {@link com.virus.pt.model.vo.RegisterVo}
     *
     * @param registerVO 接收参数
     * @param request    request
     * @return {@link com.virus.pt.common.enums.ResultEnum}
     * @throws com.virus.pt.common.exception.TipException 错误抛出
     */
    @ApiOperation(value = "注册")
    @PostMapping(value = "${config.virus.url.auth.register}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody RegisterVo registerVO, @ApiIgnore HttpServletRequest request)
            throws TipException {
        // 判断网站是否开启注册功能，注册功能都没有开启你注册啥
        if (!VirusUtils.config.getRegisterState()) {
            throw new TipException(ResultEnum.REGISTER_STATE_ERROR);
        }
        // 判断验证码是否正确
        boolean verify = captchaService.verifyCaptcha(IPUtils.getIpAddr(request), registerVO.getCaptcha());
        if (verify) {
            // 清除redis中的验证码数据
            captchaService.removeCaptcha(IPUtils.getIpAddr(request));
            // 把ip地址转保存到RegisterVO
            registerVO.setIp(IPUtils.getIpAddr(request));
            boolean create = userService.saveRollback(registerVO);
            if (create) {
                // 激活码
                String activationCode = VirusUtils.randomCode();
                // 把激活码做key email做value存储到redis中
                userService.saveActivationCodeToRedis(activationCode, registerVO.getEmail());
                // 构造激活码url 如: http://localhost:8080/login?code=ad26891d0b0790c9fff72f6dfae943a3
                String url;
                if (germPath.equals("/")) {
                    url = germHost + ":" + germPort + "/auth/login?code=" + activationCode;
                } else {
                    url = germHost + ":" + germPort + germPath + "/auth/login?code=" + activationCode;
                }
                MailVo mailVo = mailService.sendMail(registerVO.getEmail(), VirusUtils.config.getEmailSubject(),
                        VirusUtils.config.getEmailActivationTemplate(), url);
                logger.info("ip: {} email: {} 已注册, activationCode: {}, mailSendStatus: {}, mailSendError: {}",
                        IPUtils.getIpAddr(request), registerVO.getEmail(), activationCode, mailVo.getStatus(), mailVo.getError());
                return ResponseEntity.ok().build();
            } else {
                throw new TipException(ResultEnum.ACCOUNT_CREATE_ERROR);
            }
        }
        throw new TipException(ResultEnum.CAPTCHA_ERROR);
    }

    /**
     * 激活用户
     *
     * @param code 激活码
     * @return {@link com.virus.pt.common.enums.ResultEnum}
     * @throws TipException 错误抛出
     */
    @ApiOperation(value = "激活用户")
    @ApiImplicitParam(name = "code", value = "激活码", required = true)
    @GetMapping(value = "${config.virus.url.auth.activation}")
    public ResponseEntity<String> activation(@PathVariable("code") String code,
                                             @ApiIgnore HttpServletRequest request) throws TipException {
        boolean isActivation = userService.activateUser(code);
        if (isActivation) {
            logger.info("ip: {}, code: {} 用户激活", IPUtils.getIpAddr(request), code);
            return ResponseEntity.ok(JackJsonUtils.json("status", "成功"));
        }
        throw new TipException(ResultEnum.ACTIVATION_CODE_ERROR);
    }

    /**
     * 邮箱和密码登录
     * {@link com.virus.pt.model.vo.LoginVo}
     *
     * @param loginVO 接收参数
     * @return {@link com.virus.pt.common.enums.ResultEnum}
     * @throws TipException 错误抛出
     */
    @ApiOperation(value = "登录")
    @PostMapping(value = "${config.virus.url.auth.login}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> login(@RequestBody LoginVo loginVO, @ApiIgnore HttpServletRequest request)
            throws TipException {
        // 判断网站是否开启登录功能，登录功能都没有开启你登录啥
        if (!VirusUtils.config.getLoginState()) {
            throw new TipException(ResultEnum.LOGIN_STATE_ERROR);
        }
        // 判断验证码是否正确
        boolean verify = captchaService.verifyCaptcha(IPUtils.getIpAddr(request), loginVO.getCaptcha());
        if (verify) {
            // 清除redis中的验证码数据
            captchaService.removeCaptcha(IPUtils.getIpAddr(request));
            // 根据邮箱和密码登录
            UserDto userDto = userService.login(loginVO.getEmail(), loginVO.getPassword());
            if (userDto != null) {
//                userDto.setIsSigned(signRecordService.isSigned(userDto.getUid()));
                logger.info("ip: {}, userId: {} 已登录, email: {}",
                        IPUtils.getIpAddr(request), userDto.getUserAuthId(), userDto.getEmail());
                return ResponseEntity.ok(userDto);
            } else {
                throw new TipException(ResultEnum.LOGIN_ERROR);
            }
        }
        throw new TipException(ResultEnum.CAPTCHA_ERROR);
    }

    /**
     * 用户注销
     * 必需token
     *
     * @param request request
     * @return {@link com.virus.pt.common.enums.ResultEnum#SUCCESS}
     */
    @ApiOperation(value = "注销", notes = "http头部必须携带token参数")
    @ApiImplicitParam(name = "token", value = "Header携带token辨识用户",
            dataType = "string", paramType = "header", required = true)
    @PostMapping(value = "${config.virus.url.auth.logout}")
    public ResponseEntity<?> logout(@ApiIgnore HttpServletRequest request) throws TipException {
        Long userId = JwtUtils.getUserIdFromRequest(request);
        // 同步redis的数据到数据库，然后删除redis中的数据
        userService.logout(userId);
        logger.info("userId: {} 已注销", userId);
        return ResponseEntity.ok().build();
    }

    // 发送忘记密码邮件
    @ApiOperation(value = "发送重置密码邮件")
    @PostMapping(value = "${config.virus.url.auth.sendResetPassEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendResetPassEmail(@RequestBody SendResetPassEmailVo sendResetPassEmailVO,
                                                @ApiIgnore HttpServletRequest request) throws TipException {
        // 判断验证码是否正确
        boolean verify = captchaService.verifyCaptcha(IPUtils.getIpAddr(request), sendResetPassEmailVO.getCaptcha());
        if (verify) {
            // 清除redis中的验证码数据
            captchaService.removeCaptcha(IPUtils.getIpAddr(request));
            if (userAuthService.existByEmail(sendResetPassEmailVO.getEmail())) {
                // 重置密码邮件url
                String resetCode = VirusUtils.randomCode();
                // 存储在redis中
                userService.saveResetPassCode(resetCode, sendResetPassEmailVO.getEmail());
                // http://localhost:4200/reset?code=xxxxx
                String url;
                if (germPath.equals("/")) {
                    url = germHost + ":" + germPort + "/auth/reset?code=" + resetCode;
                } else {
                    url = germHost + ":" + germPort + germPath + "/auth/reset?code=" + resetCode;
                }
                MailVo mailVo = mailService.sendMail(sendResetPassEmailVO.getEmail(), VirusUtils.config.getEmailSubject(),
                        VirusUtils.config.getEmailResetPassTemplate(), url);
                logger.info("ip: {}, email: {} 发送了重置密码邮件, resetCode: {}, mailSendStatus: {}, mailSendError: {}",
                        IPUtils.getIpAddr(request), sendResetPassEmailVO.getEmail(), resetCode, mailVo.getStatus(), mailVo.getError());
                return ResponseEntity.ok().build();
            }
            throw new TipException(ResultEnum.EMAIL_ERROR);
        }
        throw new TipException(ResultEnum.CAPTCHA_ERROR);
    }

    @ApiOperation(value = "重置密码")
    @PostMapping(value = "${config.virus.url.auth.resetPass}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPass(@RequestBody ResetPassVo resetPassVO, @ApiIgnore HttpServletRequest request)
            throws TipException {
        // 判断验证码是否正确
        boolean verify = captchaService.verifyCaptcha(IPUtils.getIpAddr(request), resetPassVO.getCaptcha());
        if (verify) {
            // 清除redis中的验证码数据
            captchaService.removeCaptcha(IPUtils.getIpAddr(request));
            // 如果在redis中存在该认证码就可以重置密码
            String email = userService.existResetPassCode(resetPassVO.getCode());
            if (StringUtils.isNotBlank(email)) {
                // 重置密码
                boolean reset = userAuthService.resetPass(email, resetPassVO.getPassword());
                if (reset) {
                    userService.removeResetPassCode(resetPassVO.getCode());
                    logger.info("ip: {} email: {} 重置了密码", IPUtils.getIpAddr(request), email);
                    return ResponseEntity.ok().build();
                } else {
                    throw new TipException(ResultEnum.RESET_PASS_ERROR);
                }
            } else {
                throw new TipException(ResultEnum.RESET_PASS_CODE_ERROR);
            }
        }
        throw new TipException(ResultEnum.CAPTCHA_ERROR);
    }
}
