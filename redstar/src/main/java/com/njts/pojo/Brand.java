package com.njts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
     @TableId(type = IdType.AUTO)
     @ApiModelProperty(value = "商品类别ID")
    private Integer brandId;
    private String brandName;
    private String brandLeter;
    private String brandDesc;
}
