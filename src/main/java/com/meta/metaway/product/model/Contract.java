package com.meta.metaway.product.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Contract {
	private long contractId;
	private long productId;
	private long orderId;
	private long orderDetailId;
	private int contractYear;
	private int contractPrice;
	private int orderCount;
	private int stateType;
	private Timestamp startDate;
	private Timestamp endDate;
}
