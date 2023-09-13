package com.njts.security;

import com.njts.utils.Result;
import com.njts.utils.ToJson;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    //权限异常处理器
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            Result err = Result.err(HttpStatus.FORBIDDEN.value(), "用户无权限访问");
        ToJson.toJson(response,err);
    }
}
