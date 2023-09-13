package com.njts.service.impl;
import com.alibaba.fastjson.JSON;
import com.njts.dto.AssignAuthDto;
import com.njts.mapper.AuthMapper;
import com.njts.mapper.UserRoleMapper;
import com.njts.pojo.Auth;
import com.njts.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service

public class AuthServiceImp implements AuthService {

	//注入AuthMapper
	@Resource
	@Autowired
	private AuthMapper authMapper;
	//注入Redis模板
	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 根据用户id  得到用户权限(菜单) 树
	 */
	@Override
	public List<Auth> findAuthTreeById(int userId){
		//先从redis中查询缓存,查到的是权限(菜单)树 List<Auth>转的json串
		String authTreeListJson = redisTemplate.opsForValue().get(userId + ":authTree");
		if(StringUtils.hasText(authTreeListJson)){
			System.out.println("========redis中查到根据用户id  得到用户权限(菜单) 树");
			//redis中查到缓存
			//将json串转回权限(菜单)树List<Auth>并返回
			List<Auth> authTreeList = JSON.parseArray(authTreeListJson, Auth.class);
			return authTreeList;
		}
//------------------------- redis里没有树缓存--
         System.out.println("========redis中没有查到id的用户权限树");
		//数据库表中查询所有权限(菜单)
		List<Auth> allAuthListById = authMapper.selectAllByUserId(userId);

		//将所有权限(菜单) 转成权限(菜单)树
		List<Auth> authTreeList = allAuthToAuthTree(allAuthListById, 0);
		    //将登录用户的权限菜单树转成json串并保存到redis
		    redisTemplate.opsForValue().set(userId + ":authTree", JSON.toJSONString(authTreeList),2, TimeUnit.HOURS);
		//返回登录用户的权限菜单树
		return authTreeList;
	}

	//将所有权限转成权限树的递归算法
	public List<Auth> allAuthToAuthTree(List<Auth> allAuthList, int parentId){
		//获取父权限(菜单)id为参数parentId的所有权限(菜单)
		//【parentId最初为0,即最初查的是所有一级权限(菜单)】
		List<Auth> authList = new ArrayList<>();

		for (Auth auth : allAuthList) {
			if(auth.getParentId()==parentId){
				authList.add(auth);
			}
		}
		//查询List<Auth> authList中每个权限(菜单)的所有子级权限(菜单)
		for (Auth auth : authList) {
		//  进入递归-----------------------------------
			 List<Auth> childAuthList = allAuthToAuthTree(allAuthList, auth.getAuthId());
		//  递归出来-------------------------------------
		auth.setChildAuth(childAuthList);
		}
		return authList;
	}
//---------------------------------------------------------------------------------

	//查询整个权限(菜单)树的业务方法
	@Override
	public List<Auth> allAuthTree() {
		//先从redis中查询缓存,查到的是整个权限(菜单)树List<Auth>转的json串
		String allAuthTreeJson = redisTemplate.opsForValue().get("all:authTree");
		if(StringUtils.hasText(allAuthTreeJson)){
			//redis中查到缓存
			//将json串转回整个权限(菜单)树List<Auth>并返回
			List<Auth> allAuthTreeList = JSON.parseArray(allAuthTreeJson, Auth.class);
			return allAuthTreeList;
		}
		//redis中没有查到缓存,从数据库表中查询所有权限(菜单)
		List<Auth> allAuthList = authMapper.getAllAuth();

		//将所有权限(菜单)List<Auth>转成整个权限(菜单)树List<Auth>
		List<Auth> allAuthTreeList = allAuthToAuthTree(allAuthList, 0);

		//将整个权限(菜单)树List<Auth>转成json串并保存到redis
		redisTemplate.opsForValue().set("all:authTree", JSON.toJSONString(allAuthTreeList),1,TimeUnit.DAYS);
		//返回整个权限(菜单)树List<Auth>
		return allAuthTreeList;
	}

	@Override
	public List<String> AllAuthCodeByUserId(Integer userId) {
		return authMapper.selectAllAuthCodeByUserId(userId);
	}


	//给角色分配权限(菜单)的业务方法
	@Transactional//事务处理
	@Override
	public void assignAuth(AssignAuthDto assignAuthDto) {
//		//拿到角色id
//		Integer roleId = assignAuthDto.getRoleId();
//		//拿到给角色分配的所有权限(菜单)id
//		List<Integer> authIds = assignAuthDto.getAuthIds();
//
//		//根据角色id删除给角色已分配的所有权限(菜单)
//		authMapper.delAuthByRoleId(roleId);
//
//		//循环添加角色权限(菜单)关系
//		for (Integer authId : authIds) {
//			authMapper.insertRoleAuth(roleId, authId);
//		}
	}


	//根据用户id拿到用户全部权限id
	@Override
	public List<Integer> queryAuthIdsByUserId(Integer userId) {
		List<Integer> list = authMapper.selectAllAuthIdByUserId(userId);
		return list;
	}


}
