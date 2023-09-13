package com.njts.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

    private Integer storeId;

    private Integer brandId;

    private String productName;

    private String productNum;

    private Integer productInvent;

    private Integer typeId;

    private Integer supplyId;

    private Integer placeId;

    private Integer unitId;

    private String introduce;

    /**
     * 0 下架 1 上架
     */
    private String upDownState;

    private BigDecimal inPrice;

    private BigDecimal salePrice;

    private BigDecimal memPrice;

      @TableField(fill = FieldFill.INSERT)

    private Date createTime;

      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer createBy;

    private Integer updateBy;

    private String imgs;
    private Date productDate;
    private Date suppDate;


}
