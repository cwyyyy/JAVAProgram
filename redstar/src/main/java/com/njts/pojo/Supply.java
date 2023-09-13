package com.njts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 供货商
 * </p>
 *
 * @author chl
 * @since 2023-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Supply implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "supply_id", type = IdType.AUTO)
    private Integer supplyId;

    private String supplyNum;

    private String supplyName;

    private String supplyIntroduce;

    private String concat;

    private String phone;

    private String address;

    /**
     * 0:可用  1:不可用
     */
    @TableLogic
    private String isDelete;


}
