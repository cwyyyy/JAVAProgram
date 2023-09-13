package com.njts.security;
import com.njts.exception.BusinessException;
import com.njts.pojo.LoginUserInfo;
import com.njts.pojo.User;
import com.njts.utils.CurrentUser;
import com.njts.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;


//自定义登录验证接口的实现类
//  作用：接受前端发来的用户名密码进行验证
@Slf4j
@Service
public class LoginServiceImpl {

    @Autowired
    private TokenUtils tokenUtils;
   @Autowired
   // 框架是可以自己实现的，现在是前后端分离，手动来获取登录信息
   private AuthenticationManager authenticationManager;

    public String login(LoginUserInfo user) {
       //框架是可以自己实现的，这里手动来获取登录信息
         UsernamePasswordAuthenticationToken uToken=new UsernamePasswordAuthenticationToken(user.getUserCode(),user.getUserPwd());
        Authentication authenticate = authenticationManager.authenticate(uToken);//他需要一个这样的类型的参数，用上面这个去造。
         //这个对象获取 登录信息后 继续调用后面的过滤器进行用户认证。。。
             /* TODO 就会调用我们自定义的那个 UserDetails   来认证并返回认证信息authenticate；
             *   */
//       authenticate有值就代表认证通过了

        if (Objects.isNull(authenticate)){
            BusinessException businessException=new BusinessException("登陆失败");

        }
//-------------------------------------------认证成功后-----------------------------------
   //得到内容开始生成JWT ,并存入redis里；
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User user2 = loginUser.getUser();

		CurrentUser currentUser = new CurrentUser(user2.getUserId(), user2.getUserCode(), user2.getUserName());
		String token = tokenUtils.loginSign(currentUser, user.getUserPwd(),loginUser);//明文密码作为密钥

        return token;

    }






}
