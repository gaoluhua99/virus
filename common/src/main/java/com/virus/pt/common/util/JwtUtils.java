package com.virus.pt.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.virus.pt.common.constant.ApiConst;
import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtils {

    public static String createJWTToken(String secret, long userId, long exp) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + exp * ApiConst.SECOND_UNIT;
        return JWT.create()
                .withClaim(ApiConst.JWT_CLAIM_USER_ID, userId)
                .withIssuedAt(new Date(nowMillis))
                .withExpiresAt(new Date(expMillis))
                .sign(Algorithm.HMAC256(secret));
    }

    public static void verifierJWTToken(String secret, String token) throws TipException {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            throw new TipException(ResultEnum.TOKEN_ERROR);
        }
    }

    /**
     * 根据http请求头获取id
     *
     * @param request 请求头
     * @return
     * @throws TipException
     */
    public static Integer getUserIdFromRequest(HttpServletRequest request) throws TipException {
        if (request == null) {
            throw new TipException(ResultEnum.ARGS_ERROR);
        }
        // 检查http请求头中是否有token
        String token = request.getHeader(VirusUtils.config.getTokenName());
        return getUserId(token);
    }

    public static Integer getUserId(String token) throws TipException {
        if (StringUtils.isBlank(token)) {
            throw new TipException(ResultEnum.TOKEN_EMPTY_ERROR);
        }
        try {
            return JWT.decode(token).getClaim(ApiConst.JWT_CLAIM_USER_ID).asInt();
        } catch (JWTDecodeException e) {
            throw new TipException(ResultEnum.TOKEN_DECODE_ERROR);
        }
    }

    /**
     * 返回一定时间后的日期
     *
     * @param date   开始计时的时间
     * @param second 增加的秒
     * @return
     */
    public static Date getAfterDate(Date date, long second) {
        if (date == null) {
            date = new Date();
        }
        date.setTime(date.getTime() + second * ApiConst.SECOND_UNIT);
        return date;
    }
}
