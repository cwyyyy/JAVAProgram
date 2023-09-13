package com.njts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 仓库表
 * </p>
 *
 * @author chl
 * @since 2023-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "store_id", type = IdType.AUTO)
    private Integer storeId;

    private String storeName;

    private String storeNum;

    private String storeAddress;

    private String concat;

    private String phone;
    //追加字段
    @TableField(exist = false)
    private String product_num;


}
