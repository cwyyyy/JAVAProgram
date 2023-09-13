package com.njts.controller;
import com.google.code.kaptcha.Producer;
import com.njts.pojo.LoginUserInfo;
import com.njts.security.LoginServiceImpl;
import com.njts.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;


@RestController
@Slf4j
    @Api(tags = "登陆功能")
public class LoginController {
    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private  RedisUtil redisTemplate;

    @Resource(name = "captchaProducer")
    private Producer producer;

//验证码
    @ApiOperation("获取验证码")
    @GetMapping("/captcha/captchaImage")
    public void captchaImage(HttpServletResponse response){
      ServletOutputStream out=null;
    try {
        //生成验证码文本
        String text = producer.createText();
        log.info("img========>"+text);
        //文本保存在redis中来验证,60秒过期
        redisTemplate.set(text," ",60);
        //通过文本生成验证码图片并保存在内存中
        BufferedImage image1 = producer.createImage(text);
//        File captchaFile = new File("D:\\bishe\\hou\\redstar\\src\\main\\resources\\files\\a.jpg"); // 替换为实际的文件名
//        ImageIO.write(image1, "jpg", captchaFile);

        //把结果响应给前端
        response.setContentType("image/jpeg");
        //获取响应对象的字节输出流
        out  = response.getOutputStream();
        //通过字节输出流输出
        ImageIO.write(image1,"jpg",out);
        //刷新
        out.flush();
    } catch (IOException e) {
        throw new RuntimeException(e);
    } finally {
      if (out !=null){
          try {
              out.close();
          } catch (IOException e) {
              throw new RuntimeException(e);

          }
      }
    }
}

    /**
     * 前端传回包含：用户名，密码，验证码的loginUser
     */
    @PostMapping("/login")
    @ApiOperation("登陆")
	public Result login(@RequestBody LoginUserInfo loginUser) {
		/*
		  校验验证码：看redis里有无该验证码的键
		 */
		if(!redisTemplate.hasKey(loginUser.getVerificationCode())
        ){
			return Result.err(Result.CODE_ERR_BUSINESS, "-验证码不正确-！");
		}

        //验证登陆//创建jwt
        String token = loginService.login(loginUser);

     	return Result.ok("登录成功---！", token);
	}

   //初始化右上角当前登陆用户
    @GetMapping("/curr-user")
	public Result currUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String clientToken) {
		//从前端归还的token中解析出当前登录用户的信息
		CurrentUser currentUser = TokenUtils.getCurrentUser(clientToken);
		return Result.ok(currentUser);
	}

//退出登录
	@DeleteMapping("/logout")
	public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String clientToken) {
		//从redis 通过键来移除token
        CurrentUser currentUser = TokenUtils.getCurrentUser(clientToken);
        int userId = currentUser.getUserId();
         redisTemplate.del(userId+WarehouseConstants.HEADER_TOKEN_NAME);
        return Result.ok();
	}


}
