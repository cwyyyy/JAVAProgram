package com.njts.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njts.pojo.InStore;
import com.njts.pojo.OutStore;
import com.njts.pojo.PageR;
import com.njts.pojo.Store;
import com.njts.service.OutStoreService;
import com.njts.service.StoreService;
import com.njts.utils.Result;
import com.njts.utils.TokenUtils;
import com.njts.utils.WarehouseConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 出库单 前端控制器
 * </p>
 *
 * @author chl
 * @since 2023-09-11
 */
@RestController
@RequestMapping("/outstore")
public class OutStoreController {
    @Autowired
    private OutStoreService outStoreService;
    @Autowired
    private StoreService storeService;
    @ApiOperation("添加出库单")
    @PostMapping("outstore-add")
    public Result outstore_add(@RequestBody OutStore outStore, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        outStore.setCreateBy(TokenUtils.getCurrentUser(token).getUserId());
        outStore.setOutPrice(outStore.getSalePrice());
        boolean save = outStoreService.save(outStore);
        if (save) {
            return  Result.ok("ok");
        }else return Result.err(Result.CODE_ERR_SYS,"添加失败");

    }

     @ApiOperation("仓库列表")
     @GetMapping("store-list")
     public Result store_list() {
         LambdaQueryWrapper<Store> qw=new LambdaQueryWrapper<>();
                 qw.select(Store::getStoreId,Store::getStoreName);
          return  Result.ok(storeService.list(qw));

      }

      @ApiOperation("分页查询出库列表")
    @GetMapping("outstore-page-list")
    public Result outstore_page_list(Long pageNum, Long pageSize, OutStore outStore){
     PageR pageR=outStoreService.OutStoreToPage(pageNum,pageSize,outStore);
     return Result.ok( pageR);
 }

      @ApiOperation("出库")
    @PutMapping("outstore-confirm")
    public Result outstore_confirm(@RequestBody OutStore outStore){
    Result result= outStoreService.lastOutStore(outStore);
     return result;
 }


}

