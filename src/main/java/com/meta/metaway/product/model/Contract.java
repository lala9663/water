package com.meta.metaway.product.model;

import lombok.Data;

@Data
public class Contract {
	private long contractId;
	private long productId;
	private int contractYear;
	private int contractPrice;
}
