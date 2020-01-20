package com.virus.pt.core.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * @author intent
 * @date 2019/7/21 20:25
 * @about <link href='http://zzyitj.xyz/'/>
 */
@Getter
@Setter
@ToString
public class JwtUser implements UserDetails {
    private static final long serialVersionUID = -3876659361096147396L;
    private Long id;
    private Date created;
    private Date modified;
    private String ukEmail;
    private String passwordHash;
    private boolean isActivation;
    private boolean isDelete;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return ukEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActivation;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isDelete;
    }
}
