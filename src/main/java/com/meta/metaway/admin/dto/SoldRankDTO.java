package com.meta.metaway.admin.dto;

import lombok.Data;

@Data
public class SoldRankDTO {
    private Long productId;
    private String productName;
    private Long totalSales;
    private int orderState;
    private Long salesRank;
    private String filePath;
    private String fileType;
}
