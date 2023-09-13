package com.njts.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 采购单
 * </p>
 *
 * @author chl
 * @since 2023-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BuyListVO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "buy_id", type = IdType.AUTO)
    private Integer buyId;

    private Integer productId;

    private Integer storeId;

    private Integer buyNum;

    private Integer factBuyNum;

    private Date buyTime;

    private Integer supplyId;

    private Integer placeId;

    private String buyUser;

    private String phone;

    private String storeName;
    private  String productName;


    /**
     * 0 否 1 是
     */
    private String isIn;


}
