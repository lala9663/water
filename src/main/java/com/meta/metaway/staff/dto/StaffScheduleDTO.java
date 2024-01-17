package com.meta.metaway.staff.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.meta.metaway.admin.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffScheduleDTO {
	//staff
	private long staffId;
	private long userId;
	private String workPlace;
	private int workStatus;
	
	//schedule
	private long scheduleId;
	private LocalDateTime visitDate; //null
	private int visitCycle;  //null
	private int visitType;  //default 0
	private int visitState; //default 0
	private long orderId;
	private long returnId;
	private long orderDetailId;
	private LocalTime visitTime;
	
	//orders
	private LocalDateTime orderDate;
	private String orderName;
	private String orderAddress;
	private LocalDateTime requestDate;
	
	//user
	private String userPhone;
	
	//order_detail
	private int stateType;
	private LocalDateTime endDate;
	
	
}
