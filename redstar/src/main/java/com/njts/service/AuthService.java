package com.njts.service;

import com.njts.dto.AssignAuthDto;
import com.njts.pojo.Auth;

import java.util.List;

public interface AuthService {

	//根据用户id查询用户权限(菜单)树的业务方法
	public List<Auth> findAuthTreeById(int userId);

	//查询整个权限(菜单)树的业务方法
	public List<Auth> allAuthTree();

	//查询整个code权限(菜单)树的业务方法
	public List<String> AllAuthCodeByUserId(Integer userId);

	//给角色分配权限(菜单)的业务方法
	public void assignAuth(AssignAuthDto assignAuthDto);

    List<Integer> queryAuthIdsByUserId(Integer userId);
}
