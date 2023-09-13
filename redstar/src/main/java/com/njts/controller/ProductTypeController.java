package com.njts.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.njts.mapper.ProductTypeMapper;
import com.njts.pojo.ProductType;
import com.njts.service.ProductTypeService;
import com.njts.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "商品分类")
@CacheConfig(cacheNames = "com.njts.controller.ProductTypeController")
@RequestMapping("/productCategory")
public class ProductTypeController {
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private ProductTypeMapper productTypeMapper;

    @ApiOperation("获取商品分类树")
    @GetMapping("product-category-tree")
    @Cacheable(key = "'all:typeTree'")//由于这个共有属性，支持多线程共同访问
    public Result product_category_tree(){
        List<ProductType> productTypes = productTypeService.queryCategoryTree();
        return  Result.ok("ok",productTypes);
    }

    @ApiOperation("查询该类代码是否存在")
    @GetMapping("verify-type-code")
    public  Result verify_type_code(String typeCode){
          LambdaQueryWrapper<ProductType> qw=new LambdaQueryWrapper<>();
          qw.eq(ProductType::getTypeCode,typeCode);
          if (productTypeMapper.selectCount(qw)>0){
              return Result.ok(false);
          } else return Result.ok(true);
      }

    @ApiOperation("修改商品类别信息")
    @PutMapping("type-update")
    @CacheEvict(key = "'all:typeTree'")
    public  Result type_update(@RequestBody ProductType productType){
        LambdaQueryWrapper<ProductType> qw=new LambdaQueryWrapper<>();
        qw.eq(ProductType::getTypeName,productType.getTypeName());
        if (productTypeService.count(qw) ==0){
        if (productTypeService.updateById(productType)) {
            return Result.ok("修改成功");
        }else  return  Result.err(Result.CODE_ERR_SYS,"修改失败");}
        return Result.err(Result.CODE_ERR_SYS,"分类名称已存在");
    }


    @ApiOperation("添加类别信息")
    @PostMapping("type-add")
    @CacheEvict(key = "'all:typeTree'")
    public  Result type_add(@RequestBody ProductType productType){
         if (productTypeService.save(productType)) {
             return Result.ok("添加成功");
         }else return Result.err(Result.CODE_ERR_SYS,"添加失败");
     }


    @ApiOperation("删除类别")
    @DeleteMapping("type-delete/{id}")
    @CacheEvict(key = "'all:typeTree'")
    public  Result type_delete(@PathVariable String id){
        LambdaQueryWrapper qw =new LambdaQueryWrapper<ProductType>().eq(ProductType::getTypeId,id)
                .or().eq(ProductType::getParentId,id);
         if ( productTypeService.remove(qw)) {
             return Result.ok("删除成功");
         }else return Result.err(Result.CODE_ERR_SYS,"删除失败");
     }


}
