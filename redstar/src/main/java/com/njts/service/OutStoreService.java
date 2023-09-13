package com.njts.service;

import com.njts.pojo.OutStore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njts.pojo.PageR;
import com.njts.utils.Result;

/**
 * <p>
 * 出库单 服务类
 * </p>
 *
 * @author chl
 * @since 2023-09-11
 */
public interface OutStoreService extends IService<OutStore> {

    PageR OutStoreToPage(Long pageNum, Long pageSize, OutStore outStore);

    Result lastOutStore(OutStore outStore);
}
