package com.meta.metaway.admin.dto;

import java.time.LocalDateTime;

import com.meta.metaway.admin.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AdminReturnDTO extends AdminOrderDetailDTO{
	
	private long returnId;
	private LocalDateTime returnDate;
	private int returnPrice;
	private String returnText;
	
	public String getReturnDate() {
		return DateUtil.formatLocalDateTime(returnDate);
	}

	
}
