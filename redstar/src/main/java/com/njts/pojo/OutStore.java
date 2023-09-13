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
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 出库单
 * </p>
 *
 * @author chl
 * @since 2023-09-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OutStore implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "outs_id", type = IdType.AUTO)
    private Integer outsId;

    private Integer productId;

    private Integer storeId;

    private Integer tallyId;

    private BigDecimal outPrice;

    private Integer outNum;

    private Integer createBy;

      @TableField(fill = FieldFill.INSERT)
      @JsonFormat(pattern = "yyy-MM-dd")
    private Date createTime;

    /**
     * 0 否 1 是
     */
    private String isOut="0";

  @TableField(exist = false)
    private  BigDecimal salePrice;//售价
//追加字段
     @TableField(exist = false)//mapper层调用时不作为数据库字段
    private  String storeName;
     @TableField(exist = false)
    private String   startTime;
    @TableField(exist = false)
    private String   endTime;
    @TableField(exist = false)
    private  String productName;
    @TableField(exist = false)
    private String inPrice;
    @TableField(exist = false)
    private  String userCode;



}
