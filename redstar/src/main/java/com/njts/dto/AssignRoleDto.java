package com.njts.dto;

import com.njts.pojo.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 接收给用户分配角色前端传递的数据的Dto类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("角色Dto")
public class AssignRoleDto {

    //接收请求参数userId -- 用户id
    private Integer userId;

    //接收请求参数roleCheckList -- 给用户分配的所有角色名
    private List<String> roleCheckList;


}
