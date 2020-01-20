package com.virus.pt.core.service;

import com.virus.pt.common.enums.ResultEnum;
import com.virus.pt.common.exception.TipException;
import com.virus.pt.core.security.JwtUserFactory;
import com.virus.pt.db.service.RoleService;
import com.virus.pt.db.service.UserAuthService;
import com.virus.pt.model.dataobject.Role;
import com.virus.pt.model.dataobject.UserAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author intent
 * @date 2019/7/21 19:19
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Service
public class JwtUserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(JwtUserService.class);

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        try {
            UserAuth userAuth = userAuthService.getById(Long.parseLong(uid));
            if (userAuth == null) {
                throw new TipException(ResultEnum.USER_EMPTY_ERROR);
            }
            List<Role> roleList = roleService.getByUserAuthId(userAuth.getId());
            return JwtUserFactory.create(userAuth, roleList);
        } catch (TipException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
