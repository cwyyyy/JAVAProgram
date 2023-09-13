package com.njts.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.pojo.InStore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njts.pojo.PageR;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 入库单 Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2023-09-12
 */
public interface InStoreMapper extends BaseMapper<InStore> {
   @Select("select t1.*,t2.product_name,t2.in_price ,t3.store_name ,t4.user_code from in_store t1 join product t2 on t1.product_id=t2.product_id join store t3 on t1.store_id=t3.store_id join user_info t4 on t1.create_by=t4.user_id " +
           "${ew.customSqlSegment}")
   List<InStore> selectToPage(Page page, @Param(Constants.WRAPPER) QueryWrapper qw);
}
