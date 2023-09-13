package com.njts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 规格单位表
 * </p>
 *
 * @author chl
 * @since 2023-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "unit_id", type = IdType.AUTO)
    private Integer unitId;

    private String unitName;

    private String unitDesc;


}
