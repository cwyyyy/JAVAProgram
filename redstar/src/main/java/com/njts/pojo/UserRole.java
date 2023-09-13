package com.njts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author chl
 * @since 2023-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "user_role_id", type = IdType.AUTO)
    private Integer userRoleId;

    private Integer roleId;

    private Integer userId;



}
