package com.njts.service;

import com.njts.dto.AssignRoleDto;
import com.njts.pojo.Auth;
import com.njts.pojo.PageR;
import com.njts.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author chl
 * @since 2023-08-25
 */
public interface RoleService extends IService<Role> {

    public boolean updateUserAuthByUserId(List<Integer> authIds, Integer roleId) ;

    public List<Role> queryRolesByUserId(Integer userId);

   public List<Auth> queryAuthsTreeByRoleId(Integer roleId);

   //重新分配用户角色
   public boolean reSetRoles(AssignRoleDto assignRoleDto);

    PageR pageList(Long pageNum, Long pageSize, Role role);
}
