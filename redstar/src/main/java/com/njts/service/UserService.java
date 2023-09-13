package com.njts.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.pojo.User;
import com.njts.utils.Result;

import java.util.List;

public interface UserService {

	//根据用户名查找用户的业务方法
	public User findUserByCode(String userCode);

	//分页查询用户的业务方法
	public Page queryUserPage(Long num, Long size, User user);

	//添加用户的业务方法
	public Result saveUser(User user);

	//修改用户状态的业务方法
	public Result updateUserState(User user);

	//根据用户id删除用户的业务方法
	public int deleteUserById(Integer userId);

	//修改用户昵称的业务方法
	public Result updateUserName(User user);

	//重置密码的业务方法
	public Result resetPwd(Integer userId);

    void deleteUsersByIds(List<Integer> ids);

    boolean updateUserAuthByUserId(List<Integer> authIds, Integer userId);
}
