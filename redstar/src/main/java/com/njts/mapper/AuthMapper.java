package com.njts.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njts.pojo.Auth;
import com.njts.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AuthMapper extends BaseMapper<Auth> {
    int insert(Auth row);
    @Select("select DISTINCT a3.auth_code from user_role u1 join  role  re on  u1.role_id=re.role_id   JOIN role_auth  r2 on u1.role_id = r2.role_id join auth_info a3 on r2.auth_id = a3.auth_id  where  a3.auth_code IS NOT NULL AND a3.auth_code <> '' and   a3.auth_state=1 and re.role_state=1 and u1.user_id=#{userId}")
    List<String> selectAllAuthCodeByUserId(Integer userId);

    @Select("select r2.auth_id from user_role u1 join  role  re on  u1.role_id=re.role_id  JOIN role_auth  r2 on u1.role_id = r2.role_id   where  " +
            "  u1.user_id=#{userId}")
    List<Integer> selectAllAuthIdByUserId(Integer userId);
     @Select("select DISTINCT a3.* from user_role u1 join  role  re on  u1.role_id=re.role_id   JOIN role_auth  r2 on u1.role_id = r2.role_id join auth_info a3 on r2.auth_id = a3.auth_id  where    a3.auth_state=1 and re.role_state=1 and u1.user_id=#{userId}")
    List<Auth> selectAllByUserId(Integer userId);
    @Select (" select * from auth_info where   auth_info.auth_state=1 ")
    List<Auth> getAllAuth();
}