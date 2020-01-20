package com.virus.pt.core.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

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
    private Integer id;
    private String email;
    private String password;
    private boolean isActivation;
    private boolean isDelete;
    private Long created;
    private Long modify;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
