package com.virus.pt.core.security;

import com.virus.pt.model.dataobject.Role;
import com.virus.pt.model.dataobject.UserAuth;
import com.virus.pt.model.util.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author intent
 * @date 2019/7/21 20:25
 * @about <link href='http://zzyitj.xyz/'/>
 */
public class JwtUserFactory {
    public static JwtUser create(UserAuth userAuth, List<Role> roleList) {
        JwtUser jwtUser = new JwtUser();
        BeanUtils.copyFieldToBean(userAuth, jwtUser);
        jwtUser.setAuthorities(mapToGrantedAuthorities(roleList));
        return jwtUser;
    }

    private static Collection<? extends GrantedAuthority> mapToGrantedAuthorities(List<Role> roleList) {
        return roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }
}
