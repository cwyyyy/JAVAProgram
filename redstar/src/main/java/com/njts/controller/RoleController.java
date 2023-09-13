package com.njts.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.dto.AssignAuthDto;
import com.njts.dto.AssignRoleAuthDto;
import com.njts.mapper.RoleAuthMapper;
import com.njts.pojo.Auth;
import com.njts.pojo.PageR;
import com.njts.pojo.Role;
import com.njts.pojo.RoleAuth;
import com.njts.service.RoleService;
import com.njts.utils.CurrentUser;
import com.njts.utils.Result;
import com.njts.utils.TokenUtils;
import com.njts.utils.WarehouseConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")//不加tags不显示

public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleAuthMapper roleAuthMapper;

    @ApiOperation("获取所有角色集合")
    @GetMapping("role-list")
    public Result role_list(){
        //获取所有角色列表
        List<Role> roleList=roleService.list();
        return Result.ok(roleList);
    }

    //角色列表分页查询
    @ApiOperation("获取分页后的角色集合")
    @GetMapping("role-page-list")
    public Result role_page_list(@ApiParam("第几页") Long pageSize, @ApiParam("每页显示多少条数据")Long pageNum, Long totalNum, Role role){
        Page page = roleService.pageList(pageNum, pageSize,role);
        PageR pageR = new PageR(page.getCurrent(), page.getSize(), page.getTotal(), page.getPages(), page.getRecords());
        return Result.ok(pageR);
    }

    @ApiOperation("添加角色")
    @PostMapping("role-add")
    public Result role_add(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)String token,@RequestBody Role role){
        CurrentUser currentUser = TokenUtils.getCurrentUser(token);
        //由谁添加
        role.setCreateBy(currentUser.getUserId());
        boolean save = roleService.save(role);
        if (save){
            return Result.ok("添加成功");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"添加失败");
    }

    @ApiOperation("修改角色状态")
    @PutMapping("role-state-update")
    public Result role_state_update(@RequestBody Role role, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)String token){
        CurrentUser currentUser = TokenUtils.getCurrentUser(token);
        //由谁修改
        role.setUpdateBy(currentUser.getUserId());
        boolean update = roleService.updateById(role);
        if (update){
            return Result.ok("修改成功--！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败--!");
    }

    @ApiOperation("修改角色信息")
    @PutMapping("role-update")
    public Result role_update(@RequestBody Role role, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME)String token){
        CurrentUser currentUser = TokenUtils.getCurrentUser(token);
        //由谁修改
        role.setUpdateBy(currentUser.getUserId());
        boolean update = roleService.updateById(role);
        if (update){
            return Result.ok("修改成功--！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败--!");
    }

    @ApiOperation("删除角色信息")
    @DeleteMapping("role-delete/{id}")
    public Result role_delete(@PathVariable Integer id){

        boolean removeById = roleService.removeById(id);
        if (removeById){
            return Result.ok("删除成功--！");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"删除失败--!");
    }

    @ApiOperation("返回角色拥有的权限id列表")
    @GetMapping("role-auth")
    public Result role_auth(@ApiParam("角色id") Integer roleId){
        LambdaQueryWrapper<RoleAuth> qw=new LambdaQueryWrapper<>();
        qw.eq(RoleAuth::getRoleId,roleId);
        qw.select(RoleAuth::getAuthId);
        List<Object> list = roleAuthMapper.selectObjs(qw);
        return Result.ok(list);
    }

    @ApiOperation("修改角色权限")
    @PutMapping("/auth-grant")
	public  Result auth_grant(@RequestBody AssignAuthDto assignAuthDto){
		boolean b = roleService.updateUserAuthByUserId(assignAuthDto.getAuthIds(), assignAuthDto.getRoleId());
        if (b) {return Result.ok("修改成功---！");
		}
		return Result.err(Result.CODE_ERR_BUSINESS,"添加失败");
	}
}

