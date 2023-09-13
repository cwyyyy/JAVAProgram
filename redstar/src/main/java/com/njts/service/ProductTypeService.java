package com.njts.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njts.pojo.ProductType;

import java.util.List;

public interface ProductTypeService  extends IService<ProductType> {
     List<ProductType> queryCategoryTree();
}
