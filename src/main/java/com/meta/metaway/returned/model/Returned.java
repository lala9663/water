package com.meta.metaway.returned.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Returned {
	private long returnId;
	private long orderDetailId;
	private Timestamp returnDate;
	private int returnPrice;
	private String returnText;
	private int StateType;
}
