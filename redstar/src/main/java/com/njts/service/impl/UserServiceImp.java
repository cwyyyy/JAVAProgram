package com.njts.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.mapper.RoleAuthMapper;
import com.njts.mapper.UserMapper;
import com.njts.mapper.UserRoleMapper;
import com.njts.pojo.RoleAuth;
import com.njts.pojo.User;
import com.njts.pojo.UserRole;
import com.njts.service.RoleAuthService;
import com.njts.service.UserService;
import com.njts.utils.Result;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserServiceImp  implements UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	//注入UserMapper
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleAuthMapper roleAuthMapper;
	@Autowired
	private  UserRoleMapper userRoleMapper;
	@Autowired
	private RoleAuthService roleAuthService;

	//根据用户名查找用户的业务方法
	@Override
	public User findUserByCode(String userCode) {
		LambdaQueryWrapper<User> qw=new LambdaQueryWrapper<>();
		qw.eq(User::getUserCode,userCode);
		return userMapper.selectOne(qw);
	}

	//分页查询用户列表的业务方法
	@Override
	public Page queryUserPage(Long num,Long size, User user) {
       Page<User> page = new Page<>(num, size);
         LambdaQueryWrapper<User> qw =new LambdaQueryWrapper<>();
		 //condition是否执行，数据为空不执行----返回false
		 qw
				 .like(StringUtils.isNotBlank(user.getUserCode()),User::getUserCode,user.getUserCode())
				 .eq(StringUtils.isNotBlank(user.getUserState()),User::getUserState,user.getUserState())
				 .eq(StringUtils.isNotBlank(user.getUserType()),User::getUserType,user.getUserType());

        // 创建查询条件
		Page<User> userPage = userMapper.selectPage(page, qw);
		return userPage;
	}

	//添加用户的业务方法
	@Override
	public Result saveUser(User user) {
		log.info("name=========="+user.getUserCode());
		//根据用户名查询用户
		LambdaQueryWrapper<User> qw=new LambdaQueryWrapper<User>();
		qw.eq(User::getUserCode,user.getUserCode());
		User oldUser = userMapper.selectOne(qw);
		if(oldUser!=null){//用户已存在
			return Result.err(Result.CODE_ERR_BUSINESS, "该用户已存在！");
		}
		//用户不存在,对密码加密,添加用户
		String userPwd = passwordEncoder.encode(user.getUserPwd());
		user.setUserPwd(userPwd);
		userMapper.insert(user);
		return Result.ok("添加用户成功！");
	}

	//修改用户状态的业务方法
	@Override
	public Result updateUserState(User user) {

		//根据用户id修改用户状态
		int i = userMapper.updateById(user);
		if(i>0){
			return Result.ok("修改成功！");
		}
		return Result.err(Result.CODE_ERR_BUSINESS, "修改失败！");
	}

	//根据用户id删除用户的业务方法
	@Override
	public int deleteUserById(Integer userId) {
		//根据用户id修改用户状态为删除状态
		return userMapper.deleteById(userId);
	}

	//修改用户昵称的业务方法
	@Override
	public Result updateUserName(User user) {
		//根据用户id修改用户昵称
		int i = userMapper.updateById(user);
		if(i>0){//修改成功
			return Result.ok("用户修改成功！");
		}
		//修改失败
		return Result.err(Result.CODE_ERR_BUSINESS, "用户修改失败！");
	}

	//重置密码的业务方法
	@Override
	public Result resetPwd(Integer userId) {

		//创建User对象并保存用户id和加密后的重置密码123456
		User user = new User();
		user.setUserId(userId);
		user.setUserPwd(passwordEncoder.encode("123456"));

		//根据用户id修改密码
		int i = userMapper.updateById(user);

		if(i>0){//密码修改成功
			return Result.ok("密码重置成功！");
		}
		//密码修改失败
		return Result.err(Result.CODE_ERR_BUSINESS, "密码重置失败！");
	}
   //批量删除用户
	@Override
	public void deleteUsersByIds(List<Integer> ids) {
		userMapper.deleteBatchIds(ids);
	}


   //修改用户权限
	@Transactional
	@Override
	public boolean updateUserAuthByUserId(List<Integer> authIds, Integer userId) {
		//删除用户对应的角色的权限
		//先根据用户id得到用户角色id(有可能不止一个角色)???
		LambdaQueryWrapper<UserRole> qw=new LambdaQueryWrapper<>();
		qw.eq(UserRole::getUserId,userId);
		UserRole userRole = userRoleMapper.selectOne(qw);
		List<UserRole> userRoles = userRoleMapper.selectList(qw);
		LambdaQueryWrapper<RoleAuth> qw1=new LambdaQueryWrapper<>();

		qw1.eq(RoleAuth::getRoleId,userRole.getRoleId());
		roleAuthMapper.delete(qw1);
	//重新添加用户的角色的权限id
		//创建实例列表
	    List<RoleAuth>  AuthList= authIds.stream().map(res->new RoleAuth(null,userRole.getRoleId(),res)).collect(Collectors.toList());
	return  roleAuthService.saveBatch(AuthList);
	}
}
