package com.njts.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
//返回给前端的页码格式
public class PageR implements Serializable {
   private Long pageNum;
   private Long pageSize;
   private Long totalNum;
   private Long pageCount;
   private List<Object> resultList;
}
