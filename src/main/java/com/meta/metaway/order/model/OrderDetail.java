package com.meta.metaway.order.model;

import java.sql.Date;

import lombok.Data;

@Data
public class OrderDetail {
    private Long orderDetailId;
    private Long orderId;
    private Long productId;
    private Long contractId;
    private int orderCount;
    private Date startDate;
    private Date endDate;
    private String productModel;
    private Double orderPrice;
}
