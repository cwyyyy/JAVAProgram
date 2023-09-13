package com.njts.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.dto.AssignRoleAuthDto;
import com.njts.dto.AssignRoleDto;
import com.njts.pojo.Auth;
import com.njts.pojo.PageR;
import com.njts.pojo.Role;
import com.njts.pojo.User;
import com.njts.service.AuthService;
import com.njts.service.RoleService;
import com.njts.service.UserService;
import com.njts.utils.CurrentUser;
import com.njts.utils.Result;
import com.njts.utils.TokenUtils;
import com.njts.utils.WarehouseConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('/user/index')")
@Api(tags = "用户管理")
public class UserController {
	@Autowired
	private AuthService authService;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@ApiOperation("获取用户权限树")
	@GetMapping("/auth-list")
	public Result authList(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String clientToken) {
		//从前端归还的token中解析出当前登录用户的信息
		CurrentUser currentUser = tokenUtils.getCurrentUser(clientToken);

		//根据用户id 获取用户权限(菜单)树
		List<Auth> authTreeList = authService.findAuthTreeById(currentUser.getUserId());
		//响应
		return Result.ok(authTreeList);
	}


	@ApiOperation("查询用户并分页")
	@GetMapping("/user-list")
	public Result userListPage(@ApiParam("查询的当前页码") Long pageSize,@ApiParam("每页查询条数") Long pageNum, Long totalNum,@ApiParam("条件查询的用户条件") User user){
		Page Page = userService.queryUserPage(pageNum, pageSize, user);
		PageR page =new PageR(pageNum,pageSize,	Page.getTotal(),Page.getPages(),Page.getRecords());
		return Result.ok(page);
	}

	@ApiOperation("添加用户")
	@PostMapping("/addUser")
	public Result addUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
		//获取当前登录的用户
		CurrentUser currentUser = tokenUtils.getCurrentUser(token);
		int createBy = currentUser.getUserId();
		user.setCreateBy(createBy);
		//执行业务
		Result result = userService.saveUser(user);
		return result;
	}


	@ApiOperation("修改用户状态")
	@PutMapping("/updateState")
	public Result updateUserState(@RequestBody User user,
								  @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
		//获取当前登录的用户
		CurrentUser currentUser = tokenUtils.getCurrentUser(token);
		//获取当前登录的用户id,即修改用户的用户id
		int updateBy = currentUser.getUserId();
		//设置修改用户的用户id和修改时间
		user.setUpdateBy(updateBy);
		//执行业务
		Result result = userService.updateUserState(user);

		//响应
		return result;
	}

	@ApiOperation("查询用户已分配的角色，已禁用角色也会查询")
	@GetMapping("/user-role-list/{userId}")
	public Result userRoleList(@PathVariable Integer userId){
		//执行业务

		List<Role> roleList = roleService.queryRolesByUserId(userId);
		//响应
		return Result.ok(roleList);
	}

	@ApiOperation("用户重新分配角色")
	@PutMapping("/assignRole")
	public Result assignRole(@RequestBody AssignRoleDto assignRoleDto){
		boolean reSetRoles = roleService.reSetRoles(assignRoleDto);
		if (reSetRoles) {
			//响应
		return Result.ok("分配角色成功！");
		}
		return Result.err(Result.CODE_ERR_BUSINESS,"更新失败");//无效此处，在服务层抛出？
	}


	@ApiOperation("删除用户")
	@GetMapping("/deleteUser/{userId}")
	public Result deleteUser(@PathVariable Integer userId){
		//执行业务
		userService.deleteUserById(userId);
		//响应
		return Result.ok("用户删除成功！");
	}


	@ApiOperation("修改用户昵称")
	@PutMapping("/updateUser")
	public Result updateUser(@RequestBody User user, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
		//获取当前登录的用户
		CurrentUser currentUser = tokenUtils.getCurrentUser(token);
		//获取当前登录的用户id -- 修改用户的用户id
		int updateBy = currentUser.getUserId();

		user.setUpdateBy(updateBy);

		//执行业务
		Result result = userService.updateUserName(user);

		//响应
		return result;
	}


	@ApiOperation("重置密码")
	@PutMapping("/updatePwd/{userId}")
	public Result resetPassWord(@PathVariable Integer userId){
		//执行业务
		Result result = userService.resetPwd(userId);
		//响应
		return result;
	}

    @ApiOperation("得到用户的权限id列表")
	@GetMapping("/user-auth")
	public Result userAuth(Integer userId){
	List<Integer> authIds=authService.queryAuthIdsByUserId(userId);
		return Result.ok(authIds);
	}

    @ApiOperation("批量删除用户")
	@DeleteMapping("/deleteUserList")
	public  Result deleteUserList(@RequestBody List<Integer> ids){
		userService.deleteUsersByIds(ids);
     return   Result.ok("删除成功");
	}

	@ApiOperation("修改用户权限")
    @PutMapping("/auth-grant")
	public  Result auth_grant(@RequestBody AssignRoleAuthDto assignRoleAuthDto){
//		boolean b = userService.updateUserAuthByUserId(assignRoleAuthDto.getAuthIds(), assignRoleAuthDto.getUserId());
//		if (b) {return Result.ok("修改成功---！");
//		}
//		return Result.err(Result.CODE_ERR_BUSINESS,"添加失败");
		return  Result.err(Result.CODE_ERR_BUSINESS,"暂不支持修改，请通过修改角色来修改权限！！");
	}

	//导出数据
	@GetMapping("/exportTable")
	public  Result exportTable(){

    return null;
	}

}
