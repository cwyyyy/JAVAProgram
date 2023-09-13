package com.njts.service;

import com.njts.pojo.Store;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njts.utils.Result;
import com.njts.vo.StoreCountVO;

import java.util.List;

/**
 * <p>
 * 仓库表 服务类
 * </p>
 *
 * @author chl
 * @since 2023-09-07
 */
public interface StoreService extends IService<Store> {

    Result queryStoreProductNums();
}
