package com.njts.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_info")
@ApiModel(value = "用户对象")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
     @ApiModelProperty(value = "用户ID")
    private Integer userId;

      @ApiModelProperty(value = "用户名")
    private String userCode;

    private String userName;

    private String userPwd;

    private String userType;

    private String userState;

    @TableLogic //只要加上这个注解，默认为”是否以删除“的判定字段，有他存在该表的所有删除操作变为逻辑删除
    private String isDelete;

    private Integer createBy;

  @TableField(fill = FieldFill.INSERT)
    private Date createTime;
  @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer updateBy;

}