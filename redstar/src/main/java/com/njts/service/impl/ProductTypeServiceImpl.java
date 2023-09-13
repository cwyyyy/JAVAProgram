package com.njts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njts.mapper.ProductTypeMapper;
import com.njts.pojo.ProductType;
import com.njts.service.ProductService;
import com.njts.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductTypeServiceImpl  extends ServiceImpl<ProductTypeMapper,ProductType> implements ProductTypeService  {
     @Autowired
     private ProductTypeMapper productTypeMapper;

     //查询所有商品类别的分类树
      @Override
     public List<ProductType> queryCategoryTree()
     {
          List<ProductType> productTypes = productTypeMapper.selectAllList();
        return   suanFa(productTypes,0);
     }

     //分类树算法:返回第一级分类列表，第一级列表的每个对象里各自封装了下一级分类依次嵌套
     public  List<ProductType> suanFa(List<ProductType> productTypes,Integer pid)
     {
          //先拿一级列表
          List<ProductType> firstLevelType=new ArrayList<>();
          for (ProductType productType : productTypes)
          {
               if (productType.getParentId().equals(pid))
               {
                    firstLevelType.add(productType);
               }
          }

          //向一级列表里的每个 类别对象 嵌套下一级类别列表（调用递归算法）
          for (ProductType productType : firstLevelType) {
               List<ProductType> productTypes1 = suanFa(productTypes, productType.getTypeId());//此级的类别id就是被嵌套对象的parentId;
              productType.setChildProductCategory(productTypes1);//把下一级的类别对象嵌套到父类的对象里
          }

          //返回嵌套好的分类列表
          return firstLevelType;
     }



}

