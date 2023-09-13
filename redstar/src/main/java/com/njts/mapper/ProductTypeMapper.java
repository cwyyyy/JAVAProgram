package com.njts.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njts.pojo.ProductType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductTypeMapper extends BaseMapper<ProductType> {
@Select("select * from product_type")
    List<ProductType> selectAllList();
}
