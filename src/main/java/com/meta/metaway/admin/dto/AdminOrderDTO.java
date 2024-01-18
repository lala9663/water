package com.meta.metaway.admin.dto;

import java.time.LocalDateTime;

import com.meta.metaway.admin.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderDTO {
	private long orderId;
	private long userId;
	private String orderContent;
	private LocalDateTime orderDate;
	private LocalDateTime requestDate;
	private String orderName;
	private int orderState;
	private String orderAddress;
	private int orderPrice;
	private int rentalPrice;
	private String zipcode;
	private String streetadr;
	private String detailadr;
	
	private long orderDetailId;
	private String productModel;
	private String productName;
	private int orderCount;
	
	private int page;
	
	//날짜 포맷
    public String getOrderDate() {
        return DateUtil.formatLocalDateTime(orderDate);
    }
    
    public String getOrderState() {
    	if (orderState == 0) {
            return "배정 대기";
        } else if (orderState == 1) {
            return "배정 완료";
        } else if (orderState == 2) {
            return "주문 취소";
        } else if (orderState == 3) {
        	return "배송 완료";
    	} else {
            return "알 수 없음";
        }
    }
}
