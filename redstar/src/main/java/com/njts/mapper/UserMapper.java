package com.njts.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njts.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

}