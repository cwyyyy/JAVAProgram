package com.njts.security;

import com.njts.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUser implements UserDetails , Serializable {

    private User user;
    private   List<GrantedAuthority> stringList;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list=stringList;
        return list;
    }

    @Override
    public String getPassword() {

        return user.getUserPwd();
    }

    @Override
    public String getUsername() {

        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        if ("0".equals(user.getUserState())){
            System.out.println("========假");
            return  false;
        }
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
