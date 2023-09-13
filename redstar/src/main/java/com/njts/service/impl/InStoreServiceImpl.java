package com.njts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.exception.BusinessException;
import com.njts.pojo.InStore;
import com.njts.mapper.InStoreMapper;
import com.njts.pojo.PageR;
import com.njts.pojo.Product;
import com.njts.service.InStoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njts.service.ProductService;
import com.njts.service.StoreService;
import com.njts.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 入库单 服务实现类
 * </p>
 *
 * @author chl
 * @since 2023-09-12
 */
@Service
public class InStoreServiceImpl extends ServiceImpl<InStoreMapper, InStore> implements InStoreService {
@Autowired
private InStoreMapper inStoreMapper;
@Autowired
private ProductService productService;

    @Override
    public PageR queryToPage(Long pageNum, Long pageSize, InStore inStore) {
        Page page=new Page<>(pageNum,pageSize);
        QueryWrapper qw=new QueryWrapper();
        qw.eq(inStore.getStoreId()!=null,"t1.store_id",inStore.getStoreId());
        qw.like(StringUtils.isNotBlank((inStore.getProductName())),"product_name",inStore.getProductName());
        qw.le(StringUtils.isNotBlank(inStore.getEndTime()),"t1.create_time",inStore.getEndTime());
        qw.ge(StringUtils.isNotBlank(inStore.getStartTime()),"t1.create_time",inStore.getStartTime());
        List buyLists = inStoreMapper.selectToPage(page, qw);
        PageR pageR=new PageR(pageNum,pageSize,page.getTotal(),page.getPages(), buyLists);
        return pageR;
    }

    //最终入库
    @Transactional
    @Override
    public Result lastInStore(InStore inStore) {
        //1.修改为入库状态
        LambdaUpdateWrapper<InStore> qw=new LambdaUpdateWrapper<>();
        qw.set(InStore::getIsIn,1);
        qw.eq(InStore::getInsId,inStore.getInsId());
        int update = inStoreMapper.update(inStore, qw);
        if (update>0)
        {
         //2.库存数量增加
          LambdaQueryWrapper<Product> qw3=new LambdaQueryWrapper<>();
          qw3.select(Product::getProductInvent);
          qw3.eq(Product::getProductId,inStore.getProductId());
          Product nums = productService.getOne(qw3);
          int nums2=nums.getProductInvent()+inStore.getInNum();
          LambdaUpdateWrapper<Product> qw2=new LambdaUpdateWrapper<>();
          qw2.eq(Product::getProductId,inStore.getProductId());
          qw2.set(Product::getProductInvent,nums2);
            boolean update1 = productService.update(qw2);
            if (update1){
                return Result.ok("入库成功");
            }
            //不抛出异常事务不执行
             throw new BusinessException("入库失败,请稍后重试");
        }
        return Result.err(Result.CODE_ERR_SYS,"入库失败");
    }
}
