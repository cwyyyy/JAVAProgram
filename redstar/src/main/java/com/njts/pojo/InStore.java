package com.njts.pojo;

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
 * 入库单
 * </p>
 *
 * @author chl
 * @since 2023-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InStore implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "ins_id", type = IdType.AUTO)
    private Integer insId;

    private Integer storeId;

    private Integer productId;

    private Integer inNum;

    private Integer createBy;

      @TableField(fill = FieldFill.INSERT)
      @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 0 否 1 是
     */
    private String isIn="0";
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
