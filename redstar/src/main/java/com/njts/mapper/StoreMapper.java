package com.njts.mapper;

import com.njts.pojo.Store;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njts.vo.StoreCountVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 仓库表 Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2023-09-07
 */
public interface StoreMapper extends BaseMapper<Store> {

    @Select("select t1.store_id,t1.store_name,IFNULL(sum(t2.product_invent),0) as total_invent from store t1 join product t2 on t1.store_id=t2.store_id group by t1.store_id")
    List<StoreCountVO> selectStoreProductNums();
}
