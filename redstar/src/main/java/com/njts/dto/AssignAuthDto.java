package com.njts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 接收给角色分配权限(菜单)前端传递的数据的Dto类:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssignAuthDto {

    //接收请求参数roleId -- 角色id
    private Integer roleId;

    //接收请求参数authIds -- 给角色分配的所有权限(菜单)的id
    private List<Integer> authIds;
}
