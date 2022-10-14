package com.stussy.stussyclone20220930syw.security;

import com.stussy.stussyclone20220930syw.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    //GrantedAuthority권한객체이다 이녀석을 상속받은 녀석들만 와일드카드
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> anthorities = new ArrayList<GrantedAuthority>();
        //getRole, getName <- GratedAuthority 객체들이다 //  Authorities 객체가생성.
        anthorities.add(() -> user.getRole().getName());
        return anthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
