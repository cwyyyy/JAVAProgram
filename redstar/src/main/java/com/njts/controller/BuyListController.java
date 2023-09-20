package com.njts.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.njts.pojo.BuyList;
import com.njts.pojo.InStore;
import com.njts.pojo.Store;
import com.njts.pojo.User;
import com.njts.service.BuyListService;
import com.njts.service.StoreService;
import com.njts.utils.Result;
import com.njts.utils.WarehouseConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/purchase")
@Api(tags = "采购")
public class BuyListController {
    @Autowired
    private BuyListService buyListService;
    @Autowired
    private StoreService storeService;


    @ApiOperation("添加采购单")
    @PostMapping("purchase-add")
     public Result purchase_add(@RequestBody BuyList buyList){
        boolean save = buyListService.save(buyList);
        if (save){
             return Result.ok("采购采购");
        } else  return Result.err(Result.CODE_ERR_SYS,"添加失败");
    }

     @ApiOperation("分页查询采购单")
     @GetMapping("purchase-page-list")
     public Result purchase_page_list(Long pageSize,Long pageNum,BuyList buyList) {
         return Result.ok(buyListService.queryPage(pageSize,pageNum,buyList));

     }

     @ApiOperation("仓库列表")
     @GetMapping("store-list")
     public Result store_list() {
         LambdaQueryWrapper<Store> qw=new LambdaQueryWrapper<>();
                 qw.select(Store::getStoreId,Store::getStoreName);
          return  Result.ok(storeService.list(qw));

      }
     @ApiOperation("修改采购数量")
     @PutMapping("purchase-update")
     public Result purchase_update(@RequestBody BuyList buyList) {
           LambdaUpdateWrapper<BuyList> qw = new LambdaUpdateWrapper<>();
           qw.eq(BuyList::getBuyId, buyList.getBuyId());
           qw.set(BuyList::getBuyNum, buyList.getBuyNum());
           qw.set(BuyList::getFactBuyNum, buyList.getFactBuyNum());
           if (buyListService.update(qw)) {
               return Result.ok("修改成功");
           } else return Result.err(Result.CODE_ERR_SYS, "修改失败");
       }

     @ApiOperation("删除采购订单")
     @DeleteMapping("purchase-delete/{id}")
     public Result purchase_delete(@PathVariable Integer id) {
           if (buyListService.removeById(id)) {
               return Result.ok("删除成功");
           } else return Result.err(Result.CODE_ERR_SYS, "删除失败，请联系管理员");
       }

     @ApiOperation("提交入库单")
     @PostMapping("in-warehouse-record-add")
     public Result in_warehouse_record_add(@RequestBody BuyList buyList, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
               return buyListService.inStoreAndUpdate(buyList,token);
       }

     @GetMapping("/exportTable")
	@ApiOperation("返回采购列表作为导出数据")
	public  Result exportTable(@ApiParam("查询的当前页码") Long pageSize, @ApiParam("每页查询条数") Long pageNum, Long totalNum, @ApiParam("条件查询的用户条件")BuyList buyList){
		return  Result.ok(buyListService.queryPage(pageSize,pageNum,buyList).getResultList());
	}




}
