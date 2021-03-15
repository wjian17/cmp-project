package com.analizy.cmp.auth;

import com.analizy.cmp.adapter.UserDetailsServiceAdapter;
import com.analizy.cmp.domain.CmpGrantedAuthority;
import com.analizy.cmp.domain.CmpUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: wangjian
 * @date: 2021/01/19 10:27
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsServiceAdapter {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Set<CmpGrantedAuthority> authorities = new HashSet<>();
        CmpGrantedAuthority authority = new CmpGrantedAuthority("permission");
//        authorities.add(authority);
        return new CmpUser("admin","{noop}admin",authorities);
    }

}
