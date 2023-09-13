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
 * 角色权限表
 * </p>
 *
 * @author chl
 * @since 2023-09-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RoleAuth implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "role_auth_id", type = IdType.AUTO)
    private Integer roleAuthId;

    private Integer roleId;

    private Integer authId;


}
