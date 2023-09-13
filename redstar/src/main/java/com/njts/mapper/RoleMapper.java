package com.njts.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.pojo.Auth;
import com.njts.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njts.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2023-08-25
 */
public interface RoleMapper extends BaseMapper<Role> {


    @Select("SELECT r.* from role r JOIN user_role ur on r.role_id=ur.role_id WHERE user_id =#{userId}")
    public List<Role> selectRoleIdsByUserId(Integer userId);

   @Select("select a.* from role_auth ra join auth_info  a on ra.auth_id=a.auth_id where ra.role_id=#{roleId}   ")
    List<Auth> selectAuthsByRoleId(Integer roleId);

   @Select("select q1.*,q2.user_code as getCode from  role q1 join user_info q2 on q1.create_by=q2.user_id" +
            " ${ew.customSqlSegment}")
   Page<Role>  selectToPage(Page<Role> page, @Param(Constants.WRAPPER) LambdaQueryWrapper<Role> qw);
}
