package com.njts.service.impl;

import com.njts.pojo.Store;
import com.njts.mapper.StoreMapper;
import com.njts.service.StoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njts.utils.Result;
import com.njts.vo.StoreCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author chl
 * @since 2023-09-07
 */
@Service

public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {
@Autowired
private StoreMapper storeMapper;
    @Override
    public Result  queryStoreProductNums() {
    List<StoreCountVO>   list=storeMapper.selectStoreProductNums();
        return Result.ok(list);
    }
}
