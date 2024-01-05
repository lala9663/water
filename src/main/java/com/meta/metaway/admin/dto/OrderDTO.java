package com.meta.metaway.admin.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.format.Formatter;

import com.meta.metaway.admin.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
	private long orderId;
	private String orderName;
	private String orderAddress;
	private String orderContent;
	private String productModel;
	private String productName;
	private int orderCount;
	private int orderPrice;
	private LocalDateTime orderDate;
	private int orderState;
	
	private int page;
	
	//날짜 포맷
    public String getOrderDate() {
        return DateUtil.formatLocalDateTime(orderDate);
    }

    public void setFormattedOrderDate(String orderDate) {
        this.orderDate = DateUtil.parseLocalDateTime(orderDate);
    }
    
    public String getOrderState() {
        return orderState == 0 ? "대기" : "완료";
    }
}
