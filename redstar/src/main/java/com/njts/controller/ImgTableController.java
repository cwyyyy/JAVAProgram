package com.njts.controller;

import com.njts.pojo.Store;
import com.njts.service.StoreService;
import com.njts.utils.Result;
import com.njts.vo.StoreCountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "统计查询")
public class ImgTableController {
    @Autowired
    private StoreService storeService;

    @ApiOperation("统计查询各个仓库商品数")
    @GetMapping("/statistics/store-invent")
    public Result store_invent(){
return storeService.queryStoreProductNums();
    }
}
