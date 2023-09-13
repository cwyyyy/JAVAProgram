package com.njts.utils;

import com.njts.security.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 用来打包返回解析的token数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
//首页右上角信息
public class CurrentUser {

    private int userId;//用户id

    private String userCode;//用户名

    private String userName;//真实姓名


}
