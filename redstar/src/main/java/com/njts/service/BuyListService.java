package com.njts.service;

import com.njts.pojo.BuyList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njts.pojo.PageR;
import com.njts.utils.Result;

/**
 * <p>
 * 采购单 服务类
 * </p>
 *
 * @author chl
 * @since 2023-09-11
 */
public interface BuyListService extends IService<BuyList> {

    PageR queryPage(Long pageSize, Long pageNum, BuyList buyList);

    Result inStoreAndUpdate(BuyList buyList,String token);
}
