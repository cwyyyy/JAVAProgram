package com.njts.service;

import com.njts.pojo.InStore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njts.pojo.PageR;
import com.njts.utils.Result;

/**
 * <p>
 * 入库单 服务类
 * </p>
 *
 * @author chl
 * @since 2023-09-12
 */
public interface InStoreService extends IService<InStore> {

    PageR queryToPage(Long pageNum, Long pageSize, InStore inStore);

    Result lastInStore(InStore inStore);
}
