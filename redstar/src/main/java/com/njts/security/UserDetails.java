package com.njts.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njts.mapper.AuthMapper;
import com.njts.mapper.UserMapper;
import com.njts.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Slf4j
@Component
public class UserDetails implements UserDetailsService {

    @Autowired
    private AuthMapper authInfoMapper;
    @Autowired
    private  UserMapper userMapper;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//     username只是表单的第一个值=user_code
        //获得登陆用户的用户信息
        LambdaQueryWrapper<User> qw =new LambdaQueryWrapper<>();
                qw.eq(User::getUserCode,username);
        User user= userMapper.selectOne(qw);

        if (Objects.isNull(user) ) {
            throw new UsernameNotFoundException("用户不存在");
        }
        //根据用户id查所有权限
        List<String> auths = authInfoMapper.selectAllAuthCodeByUserId(user.getUserId());

           List<GrantedAuthority> authorities = auths.stream()//流
                .map(menuCode -> new SimpleGrantedAuthority(menuCode))//每次调用构造方法产生一个对应对象
                .collect(Collectors.toList());//收集放到list里



        LoginUser loginUser=new LoginUser(user,authorities);

        return loginUser;//配置用户返回信息
    }



    }

