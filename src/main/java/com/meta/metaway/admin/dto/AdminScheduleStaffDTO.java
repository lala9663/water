package com.meta.metaway.admin.dto;


import java.time.LocalDateTime;

import com.meta.metaway.admin.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminScheduleStaffDTO {

	private long scheduleId;
	private LocalDateTime visitDate; // null
	private int visitCycle; // null
	private int visitType; // default 0
	private int visitState; // default 0
	private long staffId;
	private long userId;
	private long orderId; 
	private String workPlace;
	private String userName;
	private String authorityName;

	public String getVisitDate() {
		return DateUtil.formatLocalDateTime(visitDate);
	}

}
