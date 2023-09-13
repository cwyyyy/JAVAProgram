package com.njts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author chl
 * @since 2023-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("角色实体类")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;
    @ApiModelProperty("角色名")
    private String roleName;

    private String roleDesc;

    private String roleCode;

    /**
     * 1 启用 0 禁用
     */
    private String roleState;

    private Integer createBy;

    @TableField(fill = FieldFill.INSERT)
     @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private Integer updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
     @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;
   //追加属性
    @TableField(exist = false)
    private  String getCode;

}
