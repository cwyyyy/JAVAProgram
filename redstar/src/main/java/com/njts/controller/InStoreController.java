package com.njts.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njts.pojo.InStore;
import com.njts.pojo.PageR;
import com.njts.pojo.Store;
import com.njts.pojo.User;
import com.njts.service.InStoreService;
import com.njts.service.StoreService;
import com.njts.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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


      	@GetMapping("/exportTable")
	@ApiOperation("返回入库列表作为导出数据")
	public  Result exportTable(@ApiParam("查询的当前页码") Long pageSize, @ApiParam("每页查询条数") Long pageNum, Long totalNum, @ApiParam("条件查询的用户条件") InStore inStore){
		return  Result.ok(inStoreService.queryToPage(pageNum,pageSize,inStore).getResultList());
	}
}

