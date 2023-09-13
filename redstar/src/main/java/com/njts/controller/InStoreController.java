package com.njts.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njts.pojo.InStore;
import com.njts.pojo.PageR;
import com.njts.pojo.Store;
import com.njts.service.InStoreService;
import com.njts.service.StoreService;
import com.njts.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 入库单 前端控制器
 * </p>
 *
 * @author chl
 * @since 2023-09-12
 */
@RestController
@RequestMapping("/instore")
   @Api(tags = "入库")
public class InStoreController {
    @Autowired
    private InStoreService inStoreService;
    @Autowired
    private StoreService storeService;

//storeId=1&productName=&startTime=&endTime=&pageSize=5&pageNum=1&totalNum=0
 @ApiOperation("分页查询")
 @GetMapping("instore-page-list")
    public Result instore_page_list(Long pageNum, Long pageSize, InStore inStore){
     PageR pageR=inStoreService.queryToPage(pageNum,pageSize,inStore);
     return Result.ok( pageR);
 }

     @ApiOperation("仓库列表")
     @GetMapping("store-list")
     public Result store_list() {
         LambdaQueryWrapper<Store> qw=new LambdaQueryWrapper<>();
                 qw.select(Store::getStoreId,Store::getStoreName);
          return  Result.ok(storeService.list(qw));

      }

     @ApiOperation("最终入库")
     @PutMapping("instore-confirm")
     public Result instore_confirm(@RequestBody InStore inStore) {
          return inStoreService.lastInStore(inStore);

      }
}

