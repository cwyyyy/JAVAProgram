package com.njts.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    @Select("select q1.*,q2.user_code as getCode from user_info q1 join user_info q2 on q1.create_by=q2.user_id" +
            " ${ew.customSqlSegment}")
    Page<User> selectToPage(Page<User> page,@Param(Constants.WRAPPER) QueryWrapper<User> qw);
}