package com.njts.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.pojo.BuyList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 采购单 Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2023-09-11
 */
public interface BuyListMapper extends BaseMapper<BuyList> {

    @Select("select t1.*,t2.store_name,t3.product_name from buy_list t1 join store t2 on t1.store_id=t2.store_id join product t3 on t1.product_id=t3.product_id" +
            " ${ew.customSqlSegment}")
    List<BuyList> selectToPage(Page page, @Param(Constants.WRAPPER) QueryWrapper<BuyList> qw);
}
