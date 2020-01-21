package com.virus.pt.core.config;

import com.virus.pt.core.security.JwtAuthenticationEntryPoint;
import com.virus.pt.core.security.JwtAuthorizationTokenFilter;
import com.virus.pt.core.service.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @author intent
 * @date 2019/7/21 19:07
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtUserService jwtUserService;

    // Custom JWT based security filter
    @Autowired
    private JwtAuthorizationTokenFilter authenticationTokenFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // swagger
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui").permitAll()
                // druid
                .antMatchers("/druid/**").permitAll()
                // 静态文件放行
                .antMatchers(HttpMethod.GET, "/file/captcha/**", "/file/resources/**", "/file/avatar/**").permitAll()
                // 获取公共配置、验证码、激活用户、tracker、下载种子放行
                .antMatchers(HttpMethod.GET, "/torrent/**", "/config", "/auth/captcha", "/auth/activation/**", "/user/exist/**").permitAll()
                // 登录、注册
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // disable page caching
        http.headers().frameOptions().sameOrigin().cacheControl();
    }
}
