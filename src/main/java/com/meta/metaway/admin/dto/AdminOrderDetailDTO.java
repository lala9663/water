package com.meta.metaway.admin.dto;


import java.time.LocalDateTime;

import com.meta.metaway.admin.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderDetailDTO {
	private long orderId;
	private String orderName;
	private String orderAddress;
	private String orderContent;
	private int orderPrice;
	private int rentalPrice;
	private LocalDateTime orderDate;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private LocalDateTime requestDate;
	private int orderCount;
	private String productModel;
	private String productName;
	private int contractYear;
	private int contractPrice;
	private String userPhone;
	private long staffId;
	private String workPlace;
	private long scheduleId;
	
	//날짜 포맷
    public String getOrderDate() {
        return DateUtil.formatLocalDateTime(orderDate);
    }

	public String getStartDate() {
		return  DateUtil.formatLocalDateTime(startDate);
	}

	public String getEndDate() {
		return  DateUtil.formatLocalDateTime(endDate);
	}
	
	public String getRequestDate() {
		return  DateUtil.formatLocalDateTime(requestDate);
	}

    
    
    
}


