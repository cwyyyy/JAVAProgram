package com.njts.controller;

import com.njts.pojo.*;
import com.njts.service.ProductService;
import com.njts.service.ProductTypeService;
import com.njts.utils.CurrentUser;
import com.njts.utils.Result;
import com.njts.utils.TokenUtils;
import com.njts.utils.WarehouseConstants;
import com.njts.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
@Api(tags = "商品管理")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTypeService productTypeService;

    @Value("${file.upload-path}")
    private String uploadPath;

    @Value("${file.access-path}")
    private String img_path;



    @ApiOperation("获取商品列表返回分页数据")
    @GetMapping("product-page-list")
    public Result product_page_list(ProductVO productVo, Long pageSize, Long pageNum) {
        PageR products = productService.getProducts(pageSize, pageNum, productVo);
        return Result.ok(products);
    }

    @ApiOperation("获取仓库列表")
    @GetMapping("store-list")
    public Result store_list() {
        List<Store> storeList = productService.queryStoreList();
        return Result.ok("ok", storeList);
    }

    @ApiOperation("获取品牌名称列表")
    @GetMapping("brand-list")
    public Result brand_list() {
        List<Brand> brandList = productService.queryBrandList();
        return Result.ok("ok", brandList);
    }

    @ApiOperation("获取商品类别树")
    @GetMapping("category-tree")
    public Result category_tree() {
        List<ProductType> productTypes = productTypeService.queryCategoryTree();
        return Result.ok("ok",productTypes);
    }

    @ApiOperation("获取供应商列表")
    @GetMapping("supply-list")
    public Result supply_list() {
        List<Supply> supplyList = productService.querySupplyList();
        return Result.ok("ok", supplyList);
    }

    @ApiOperation("获取产地列表")
    @GetMapping("place-list")
    public Result place_list() {
        List<Place> placeList = productService.queryPlaceList();
        return Result.ok("ok", placeList);
    }

    @ApiOperation("获取商品规格")
    @GetMapping("unit-list")
    public Result unit_list() {

        List<Unit> UnitList = productService.queryUnitList();
        return Result.ok("ok", UnitList);
    }

    @ApiOperation("添加商品")
    @PostMapping("product-add")
    public Result product_add(@RequestBody Product product, @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        return   productService.saveProduct(product,token);
    }

    @ApiOperation("添加图片")
    @PostMapping("/img-upload")
    @CrossOrigin
    public Result img_upload(MultipartFile file) {
        try {
            //图片上传的类路径目录的file对象->拿到该目录的磁盘路径->末尾加上图片名就是完整的磁盘路径->磁盘路径的file对象->把内存图片存到该对象里
            //拿到图片上传到的目录(类路径classes下的static/img/upload)的File对象
            File uploadDirFile = ResourceUtils.getFile(uploadPath);
            //拿到图片上传到的目录的磁盘路径
            String uploadDirPath = uploadDirFile.getAbsolutePath();
            //拿到图片保存到的磁盘路径
            String fileUploadPath = uploadDirPath + "\\" + file.getOriginalFilename();
            System.out.println("图片保存地址"+fileUploadPath);
            //保存图片
            file.transferTo(new File(fileUploadPath));
            //成功响应
            return Result.ok("图片上传成功！");
        } catch (IOException e) {
            //失败响应
            return Result.err(Result.CODE_ERR_BUSINESS, "图片上传失败！");
        }

    }

    @ApiOperation("商品上下架")
    @PutMapping("state-change")
    public Result state_change(@RequestBody Product product) {
      productService.updateById(product);
        return Result.ok("操作成功");
    }

    @ApiOperation("删除商品")
    @DeleteMapping("product-delete/{id}")
    public Result product_delete(@PathVariable Integer id ) {
           productService.removeById(id);
        return Result.ok("操作成功");
    }

    @ApiOperation("批量删除商品")
    @DeleteMapping("product-list-delete")
    public Result product_list_delete(@RequestBody List<Integer> ids) {
      productService.removeByIds(ids);
        return Result.ok("操作成功");
    }

    @ApiOperation("修改商品")
    @PutMapping("product-update")
    public Result product_update(@RequestBody Product product,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
       //判断图片是否更改
       if (!product.getImgs().contains(img_path)){
           product.setImgs(img_path+product.getImgs());
          }
        product.setUpdateBy(TokenUtils.getCurrentUser(token).getUserId());
        boolean b = productService.updateById(product);
          if (b){
                return Result.ok("操作成功");
          }
          else return Result.err(Result.CODE_ERR_SYS,"修改失败");
    }

@GetMapping("/exportTable")
	@ApiOperation("返回商品列表作为导出数据")
	public  Result exportTable(@ApiParam("查询的当前页码") Long pageSize, @ApiParam("每页查询条数") Long pageNum, Long totalNum, @ApiParam("条件查询的用户条件") ProductVO product){
    PageR products = productService.getProducts(pageSize, pageNum, product);
	return  Result.ok( products.getResultList());
	}
}