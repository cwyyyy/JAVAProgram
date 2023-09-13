package com.njts.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.exception.BusinessException;
import com.njts.pojo.InStore;
import com.njts.pojo.OutStore;
import com.njts.mapper.OutStoreMapper;
import com.njts.pojo.PageR;
import com.njts.pojo.Product;
import com.njts.service.OutStoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njts.service.ProductService;
import com.njts.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 出库单 服务实现类
 * </p>
 *
 * @author chl
 * @since 2023-09-11
 */
@Service
public class OutStoreServiceImpl extends ServiceImpl<OutStoreMapper, OutStore> implements OutStoreService {
@Autowired
private  OutStoreMapper outStoreMapper;
@Autowired
private ProductService productService;


//分页
    @Override
    public PageR OutStoreToPage(Long pageNum, Long pageSize, OutStore outStore) {
        Page page=new Page<>(pageNum,pageSize);
        QueryWrapper qw=new QueryWrapper();
        qw.eq(outStore.getStoreId()!=null,"t1.store_id",outStore.getStoreId());
        qw.like(StringUtils.isNotBlank((outStore.getProductName())),"product_name",outStore.getProductName());
        qw.le(StringUtils.isNotBlank(outStore.getEndTime()),"t1.create_time",outStore.getEndTime());
        qw.ge(StringUtils.isNotBlank(outStore.getStartTime()),"t1.create_time",outStore.getStartTime());
        qw.eq(StringUtils.isNotBlank(outStore.getIsOut()),"is_out",outStore.getIsOut());
        List buyLists = outStoreMapper.selectToPage(page, qw);
        PageR pageR=new PageR(pageNum,pageSize,page.getTotal(),page.getPages(), buyLists);
        return pageR;
    }
 //最终出库
    @Override
    @Transactional
    public Result lastOutStore(OutStore outStore) {
    //判断库存是否足够
        LambdaQueryWrapper<Product> qw2=new LambdaQueryWrapper<>();
        qw2.eq(Product::getProductId,outStore.getProductId());
        qw2.select(Product::getProductInvent);
        int nums=productService.getOne(qw2).getProductInvent();
        int nums2=nums-outStore.getOutNum();
        if (nums2>0)
        {
            //修改状态为出库
            LambdaUpdateWrapper<OutStore> qw=new LambdaUpdateWrapper<>();
            qw.set(OutStore::getIsOut,1);
            qw.eq(OutStore::getOutsId,outStore.getOutsId());
            int update = outStoreMapper.update(null, qw);
            if (update>0)
            {
              //库存量减少
               LambdaUpdateWrapper<Product> qw3=new LambdaUpdateWrapper<>();
               qw3.set(Product::getProductInvent,nums2);
               qw3.eq(Product::getProductId,outStore.getProductId());
               boolean update1 = productService.update(qw3);
               if (update1)
               {
                  return Result.ok("操作成功");
               }
               throw  new BusinessException("操作失败");//事务回滚
            }
            return Result.err(Result.CODE_ERR_BUSINESS,"操作失败");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"库存不足");
    }
}
