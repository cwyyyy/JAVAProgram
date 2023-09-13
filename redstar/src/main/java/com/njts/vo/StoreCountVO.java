package com.njts.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCountVO {
    private String totalInvent;
    private Integer storeId;
    private String storeName;
}
