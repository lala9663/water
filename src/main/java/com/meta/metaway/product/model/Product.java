package com.meta.metaway.product.model;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class Product {
	private long productId;
	private int formId;
	private String productModel;
	private String productName;
	private Date productDate;
	
	private List<Contract> contract;
	private List<Short> function;
}
