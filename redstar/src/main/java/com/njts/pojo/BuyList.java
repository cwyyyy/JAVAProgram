package com.njts.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class BuyList implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "buy_id", type = IdType.AUTO)
    private Integer buyId;

    private Integer productId;

    private Integer storeId;

    private Integer buyNum;

    private Integer factBuyNum;

 @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date buyTime;

    private Integer supplyId;

    private Integer placeId;

    private String buyUser;

    private String phone;

    /**
     * 0 否 1 是
     */
    private String isIn="0";
//追加字段
    @TableField(exist = false)
    private String   startTime;
    @TableField(exist = false)
    private String   endTime;
    @TableField(exist = false)
    private  String productName;
    @TableField(exist = false)
    private  String storeName;



}
