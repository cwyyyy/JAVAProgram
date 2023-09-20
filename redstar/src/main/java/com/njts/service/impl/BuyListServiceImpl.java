package com.njts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.exception.BusinessException;
import com.njts.pojo.BuyList;
import com.njts.mapper.BuyListMapper;
import com.njts.pojo.InStore;
import com.njts.pojo.PageR;
import com.njts.service.BuyListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njts.service.InStoreService;
import com.njts.utils.Result;
import com.njts.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 采购单 服务实现类
 * </p>
 *
 * @author chl
 * @since 2023-09-11
 */
@Service
public class BuyListServiceImpl extends ServiceImpl<BuyListMapper, BuyList> implements BuyListService {
    @Autowired
    private  BuyListMapper buyListMapper;
    @Autowired
    private InStoreService inStoreService;

    @Override
    public PageR queryPage(Long pageSize, Long pageNum, BuyList buyList) {
        Page page=new Page<>(pageNum,pageSize);
        QueryWrapper<BuyList> qw=new QueryWrapper<BuyList>();
        qw.like(StringUtils.isNotBlank(buyList.getBuyUser()),"buy_user",buyList.getBuyUser());
        qw.eq(buyList.getStoreId()!=null,"t1.store_id",buyList.getStoreId());
        qw.like(StringUtils.isNotBlank((buyList.getProductName())),"product_name",buyList.getProductName());
        qw.like(StringUtils.isNotBlank((buyList.getIsIn())),"is_in",buyList.getIsIn());
        qw.le(StringUtils.isNotBlank(buyList.getEndTime()),"buy_time",buyList.getEndTime());
        qw.ge(StringUtils.isNotBlank(buyList.getStartTime()),"buy_time",buyList.getStartTime());

        List buyLists = buyListMapper.selectToPage(page, qw);
        PageR pageR=new PageR(pageNum,pageSize,page.getTotal(),page.getPages(), buyLists);
        return  pageR;
    }


//提交入库单
    @Override
    //开一个事务
    @Transactional
    public Result inStoreAndUpdate(BuyList buyList,String token) {
            //先修改自己状态为以入库
            LambdaUpdateWrapper<BuyList> qw=new LambdaUpdateWrapper<>();
            qw.set(BuyList::getIsIn,1);
            qw.eq(BuyList::getBuyId,buyList.getBuyId());
            int update = buyListMapper.update(buyList, qw);

            if (update>0){
            //再生成入库单
            InStore inStore=new InStore();
            inStore.setCreateBy(TokenUtils.getCurrentUser(token).getUserId());
            inStore.setInNum(buyList.getFactBuyNum());
            inStore.setProductId(buyList.getProductId());
            inStore.setStoreId(buyList.getStoreId());
                boolean save = inStoreService.save(inStore);
                if (save) {
                    return Result.ok("入库成功");
                }
                throw new BusinessException("操作失败");
            }
        return Result.err(Result.CODE_ERR_SYS,"操作失败");
    }

}
