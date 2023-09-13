package com.njts.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author chl
 * @since 2023-09-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ProductVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer productId;
    private Integer storeId;
    private String storeName;

    private Integer brandId;
    private String brandName;

    private String productName;

    private String productNum;

    private Integer productInvent;

    private Integer typeId;
    private String typeName;

    private Integer supplyId;
    private String supplyName;

    private Integer placeId;
    private String placeName;

    private Integer unitId;
    private String unitName;

    private String introduce;
    private String upDownState;
    private BigDecimal inPrice;
    private BigDecimal salePrice;
    private BigDecimal memPrice;
    private Date createTime;
    private Date updateTime;
    private Integer createBy;
    private Integer updateBy;
    private String imgs;
     @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productDate;
      @JsonFormat(pattern = "yyyy-MM-dd")
    private Date suppDate;
    private String isOverDate;
}
