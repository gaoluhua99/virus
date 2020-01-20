package com.virus.pt.core.security;

import com.virus.pt.common.util.IPUtils;
import com.virus.pt.common.util.VirusUtils;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.common.util.JwtUtils;
import com.virus.pt.core.service.JwtUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author intent
 * @date 2019/7/21 20:42
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationTokenFilter.class);

    @Autowired
    private JwtUserService jwtUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        Integer uid = null;
        try {
            uid = JwtUtils.getUserIdFromRequest(request);
        } catch (TipException ignored) {
        }
        // 检查登录的用户
        if (uid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                String token = request.getHeader(VirusUtils.config.getTokenName());
                // 验证 token
                JwtUtils.verifierJWTToken(VirusUtils.config.getTokenSecret(), token);
                UserDetails userDetails = jwtUserService.loadUserByUsername(String.valueOf(uid));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (UsernameNotFoundException | TipException e) {
                logger.error("权限认证错误, ip: " + IPUtils.getIpAddr(request));
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
