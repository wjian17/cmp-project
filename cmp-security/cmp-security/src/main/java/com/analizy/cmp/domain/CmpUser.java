package com.analizy.cmp.domain;

import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * @author: wangjian
 * @date: 2021/01/19 13:44
 */
@Data
public class CmpUser implements UserDetails, CredentialsContainer {
    private static final long serialVersionUID = 510L;
    private String password;
    private final String username;
    private final Set<CmpGrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public CmpUser(String username, String password, Collection<? extends CmpGrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    public CmpUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends CmpGrantedAuthority> authorities) {
        if (username != null && !"".equals(username) && password != null) {
            this.username = username;
            this.password = password;
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    private static SortedSet<CmpGrantedAuthority> sortAuthorities(Collection<? extends CmpGrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null CmpGrantedAuthority collection");
        SortedSet<CmpGrantedAuthority> sortedAuthorities = new TreeSet(new AuthorityComparator());
        Iterator var2 = authorities.iterator();

        while (var2.hasNext()) {
            CmpGrantedAuthority CmpGrantedAuthority = (CmpGrantedAuthority) var2.next();
            Assert.notNull(CmpGrantedAuthority, "CmpGrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(CmpGrantedAuthority);
        }

        return sortedAuthorities;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    private static class AuthorityComparator implements Comparator<CmpGrantedAuthority>, Serializable {

        private static final long serialVersionUID = 510L;

        private AuthorityComparator() {
        }

        @Override
        public int compare(CmpGrantedAuthority g1, CmpGrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            } else {
                return g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority());
            }
        }
    }
}
