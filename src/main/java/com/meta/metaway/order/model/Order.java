package com.meta.metaway.order.model;

import java.sql.Date;
import java.util.List;

import com.meta.metaway.product.model.Contract;
import com.meta.metaway.product.model.Product;

import lombok.Data;

@Data
public class Order {
	private long orderId;
	private long userId;
	private String orderContent;
	private Date orderDate;
	private Date requestDate;
	private short orderState;
	private String orderName;
	private String orderAddress;
	private int orderPrice;
	private int rentalPrice;
	private int continuDate;
	private int orderSize;
	
	private List<Contract> contractList;
	private List<Product> productList;
}
