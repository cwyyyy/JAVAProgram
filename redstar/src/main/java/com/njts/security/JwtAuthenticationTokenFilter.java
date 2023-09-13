package com.njts.security;


import com.alibaba.fastjson.JSON;
import com.njts.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.Objects;
/*TODO
     校验JWT的过滤器,此过滤器的作用只是给过来的请求 解析jwt，不做拦截，相当于修饰一下请求，没有修饰的后面自然会拦截。
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        System.out.println("当前访问路径："+requestURI);

        //  token为空就放行让下面的拦截器拦截
        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        if (!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }

        if( requestURI.equals("/redstar/login")){
              filterChain.doFilter(request,response);
            return;
        }
//-----------------------------------------------------------------------------------------------
        //通过token拿到loginUser
        LoginUser loginUser = null;
        try {
       // 从Redis 取值，验证用户是否已经手动退出
            loginUser = (LoginUser) redisUtil.get(token);
        } catch (Exception e) {
                   //校验失败,向前端响应失败的Result对象转成的json串
           Result result = Result.err(Result.CODE_ERR_UNLOGINED, "token失效，请登录！");
           ToJson.toJson(response,result);
           return;
        }
        if (Objects.isNull(loginUser)){
            Result result = Result.err(Result.CODE_ERR_UNLOGINED, "用户已过期，请重新登录！");
            ToJson.toJson(response,result);
            return;
        }
//---------------------------------------------------
//      为了跨服务器访问，不走session,每访问一个路径都通过token生成securityContext带着，代表已认证和携带了权限信息
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        log.info("=====校验成功，securityContextHolder生成");
      //带着上下文放行 进入下一个过滤器
        filterChain.doFilter(request,response);
    }
}
