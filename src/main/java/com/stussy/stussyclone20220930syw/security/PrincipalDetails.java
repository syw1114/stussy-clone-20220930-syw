package com.stussy.stussyclone20220930syw.security;

import com.stussy.stussyclone20220930syw.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;
    public PrincipalDetails(User user) {
        this.user = user;
    }

    public PrincipalDetails(User user,Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }
    @Override
    //GrantedAuthority권한객체이다 이녀석을 상속받은 녀석들만 와일드카드
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> anthorities = new ArrayList<GrantedAuthority>();

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

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }

    @Override
    public Map<String,Object> getAttribute(String name) {
        return attributes;
    }
}
