package com.njts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.dto.AssignRoleDto;
import com.njts.exception.BusinessException;
import com.njts.mapper.RoleAuthMapper;
import com.njts.mapper.UserRoleMapper;
import com.njts.pojo.Auth;
import com.njts.pojo.Role;
import com.njts.mapper.RoleMapper;
import com.njts.pojo.RoleAuth;
import com.njts.pojo.UserRole;
import com.njts.service.RoleAuthService;
import com.njts.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njts.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author chl
 * @since 2023-08-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private  RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private  AuthServiceImp authServiceImp;
    @Autowired
    private RoleAuthMapper roleAuthMapper;
    @Autowired
    private RoleAuthService roleAuthService;



    //修改角色权限
    @Transactional
    @Override
    public boolean updateUserAuthByUserId(List<Integer> authIds, Integer roleId) {
        //先删原有单个角色的权限
        LambdaQueryWrapper<RoleAuth> qw=new LambdaQueryWrapper<>();
        qw.eq(RoleAuth::getRoleId,roleId);
        roleAuthMapper.delete(qw);
        //再为这个角色添加权限
        List<RoleAuth> roleAuths=authIds.stream().map(res->new RoleAuth(null,roleId,res)).collect(Collectors.toList());
        return  roleAuthService.saveBatch(roleAuths);
    }

    @Override
    public List<Role> queryRolesByUserId(Integer userId) {
        return roleMapper.selectRoleIdsByUserId(userId);
    }

//返回角色的权限树
    @Override
    public List<Auth> queryAuthsTreeByRoleId(Integer roleId) {
        List<Auth> auths = roleMapper.selectAuthsByRoleId(roleId);

        return  authServiceImp.allAuthToAuthTree(auths,0);
    }


 //给用户重新分配角色,
    @Transactional
    @Override
    public boolean reSetRoles(AssignRoleDto assignRoleDto) {
        //执行业务,给指定用户重新分配角色,先把原来的都删除，然后再重新添加，以免重复
        assignRoleDto.getUserId();
		//执行删除
//		    //得到该用户原本拥有的所有角色id
//			List<Role> roleList = queryRolesByUserId(assignRoleDto.getUserId());
//        List<Integer> oldIds = new ArrayList<>();
//
//            if (roleList != null)
//            {
//              for (Role role : roleList) {
//              oldIds.add(role.getRoleId());
//              }
//            }
        //根据user_id删除选取用户原来的所有角色
        QueryWrapper qw=new QueryWrapper<>();
            qw.eq("user_id",assignRoleDto.getUserId());
        userRoleMapper.delete(qw);

         //根据选择的角色名获取新角色集
        List<String> roleCheckList = assignRoleDto.getRoleCheckList();
        List<UserRole> newRole = new ArrayList<>();

        if (roleCheckList != null && !roleCheckList.isEmpty()) {
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("role_name", roleCheckList);
            List<Role> entityList = roleMapper.selectList(queryWrapper);

            newRole = entityList.stream()
            .map( role ->new UserRole(null,role.getRoleId(),assignRoleDto.getUserId()))
            .collect(Collectors.toList());

            //再统一添加
            return userRoleService.saveBatch(newRole);
        }
        else{
            throw new BusinessException("请至少勾选一项");
        }
    }

    @Override
    public Page pageList(Long pageNum, Long pageSize,Role role) {

        Page page=new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Role> qw=new LambdaQueryWrapper<>();
        qw.like(StringUtils.isNotBlank(role.getRoleCode()),Role::getRoleCode,role.getRoleCode());
        qw.like(StringUtils.isNotBlank(role.getRoleName()),Role::getRoleName,role.getRoleName());
        qw.like(StringUtils.isNotBlank(role.getRoleState()),Role::getRoleState,role.getRoleState());
        Page page1 = roleMapper.selectPage(page,qw);
        return page1;
    }

    //添加角色
    @Override
    public boolean save(Role entity) {
        return super.save(entity);
    }
}
