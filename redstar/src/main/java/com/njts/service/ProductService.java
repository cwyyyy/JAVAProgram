package com.njts.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njts.pojo.*;
import com.njts.utils.Result;
import com.njts.vo.ProductVO;

import java.util.List;

public interface ProductService extends IService<Product> {

   PageR getProducts(Long pageNum, Long pageSize, ProductVO productVo);


     List<Brand> queryBrandList();

     List<Supply> querySupplyList();

     List<Place> queryPlaceList();

     List<Store> queryStoreList();

     List<Unit> queryUnitList();

     Result saveProduct(Product product, String token);


}
