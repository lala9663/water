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
	private List<Short> function;
	
//	표시할때 추가로 더 필요한 것들
	private String imageType;
	private String imageFile;
	private String formName;
	private long contractId;
	private int contractYear;
	private int contractPrice;
	private List<String> functionList;
	private List<Contract> contract;
}
