package com.njts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 商品分类表product_type表对应的实体类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductType implements Serializable {
@TableId(type = IdType.AUTO)
    private Integer typeId;//分类id

    private Integer parentId;//上级分类id

    private String typeCode;//分类代码

    private String typeName;//分类名称

    private String typeDesc;//分类描述

   @TableField(exist = false)
 //自定义List<ProductType>集合属性,用于存储当前分类的所有子级分类
    private List<ProductType> childProductCategory;
}
