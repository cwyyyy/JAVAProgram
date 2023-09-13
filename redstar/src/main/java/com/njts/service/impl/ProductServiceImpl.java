package com.njts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njts.mapper.*;
import com.njts.pojo.*;
import com.njts.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njts.utils.CurrentUser;
import com.njts.utils.Result;
import com.njts.utils.TokenUtils;
import com.njts.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author chl
 * @since 2023-09-06
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SupplyMapper supplyMapper;

    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private PlaceMapper placeMapper;
    @Autowired
    private UnitMapper unitMapper;
    @Value("${file.access-path}")
    private String pathImg;

//商品联表分页查询
    @Override
    public PageR getProducts(Long pageNum, Long pageSize, ProductVO productVo) {
        Page<ProductVO> page1=new Page<>(pageSize,pageNum);
        QueryWrapper<ProductVO> qw=new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(productVo.getProductName()),"product_name",productVo.getProductName());
        qw.like(StringUtils.isNotBlank(productVo.getBrandName()),"brand_name",productVo.getBrandName());
        qw.like(StringUtils.isNotBlank(productVo.getPlaceName()),"place_name",productVo.getPlaceName());
        qw.like(StringUtils.isNotBlank(productVo.getTypeName()),"type_name",productVo.getTypeName());
        qw.like(StringUtils.isNotBlank(productVo.getSupplyName()),"supply_name",productVo.getSupplyName());
        qw.eq((productVo.getStoreId()!=null),"t1.store_id",productVo.getStoreId());
        Date date=new Date();
        if ("1".equals(productVo.getIsOverDate())){
            qw.le("supp_date",date);
        }else if("0".equals(productVo.getIsOverDate())) {
            qw.ge("supp_date", date);
        }
        qw.eq(StringUtils.isNotBlank(productVo.getUpDownState()),"up_down_state",productVo.getUpDownState());

        //执行分页查询!!!!!!!!!!!!!!!!!!!!!!!
        List products = productMapper.getProducts(page1, qw);
        PageR pageR=new PageR(pageNum,pageSize,page1.getTotal(),page1.getPages(),products);
        return pageR;
    }
    //添加商品
    @Override
    public Result saveProduct(Product product, String token) {
         CurrentUser currentUser = TokenUtils.getCurrentUser(token);
        product.setCreateBy(currentUser.getUserId());
      product.setImgs(pathImg+product.getImgs());
        int i = productMapper.insert(product);
        if (i > 0) {
            return Result.ok("添加成功");
        }
        else Result.err(Result.CODE_ERR_BUSINESS, "添加失败");
        return null;
    }

    //获取商品规格
    @Override
    public List<Unit> queryUnitList() {
        return unitMapper.selectList(null);
    }
//获取仓库列表

    @Override
    public List<Store> queryStoreList() {
        return storeMapper.selectList(null);
    }

    //获取品牌列表
    @Override
    public List<Brand> queryBrandList() {
        return brandMapper.selectList(null);
    }
//获取供应商列表
    @Override
    public List<Supply> querySupplyList() {
        return supplyMapper.selectList(null) ;
    }
//获取产地列表
    @Override
    public List<Place> queryPlaceList() {
        return placeMapper.selectList(null) ;
    }
}
