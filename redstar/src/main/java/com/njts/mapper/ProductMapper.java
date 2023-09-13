package com.njts.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.pojo.Product;
import com.njts.vo.ProductVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductMapper extends BaseMapper<Product> {
    @Select("    select  t1.*,t2.store_name, t3.brand_name, t4.type_name, t5.supply_name, t6.place_name, t7.unit_name from \n" +
          "  product t1  JOIN store t2 on t1.store_id=t2.store_id join brand t3 on t1.brand_id=t3.brand_id JOIN product_type t4 on t1.type_id=t4.type_id JOIN supply t5\n" +
          "\ton t1.supply_id=t5.supply_id JOIN\n" +
          " place t6 on t1.place_id=t6.place_id  JOIN unit t7 on t1.unit_id=t7.unit_id   ${ew.customSqlSegment}")
  List<ProductVO> getProducts(Page<ProductVO> page, @Param(Constants.WRAPPER) QueryWrapper<ProductVO> queryWrapper);
}
