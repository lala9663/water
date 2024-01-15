package com.meta.metaway.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminStaffDTO {
	private long staffId;
	private long userId;
	private String userAccount;
	private String userEmail;
	private String userPhone;
	private String userName;
	private String userAddress;
	private String userAge;
	private String workPlace;
	private int workStatus;
	private String authorityName;
	
	private int page;
	
	public String getWorkStatus() {
    	if (workStatus == 1) {
            return "배정완료";
        } else if (workStatus == 0) {
            return "배정대기";
        } else {
            return "알 수 없음";
        }
    }
	
	public String getAuthorityName() {
	    if ("ROLE_DRIVER".equals(authorityName)) {
	        return "DRIVER";
	    } else if ("ROLE_CODI".equals(authorityName)) {
	        return "CODI";
	    } else {
	        return "알 수 없음";
	    }
	}
	
}