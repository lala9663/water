package com.meta.metaway.staff.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class StaffListDTO {

    private Long order_id;
    private String product_name;
    private String order_name;
    private Date order_date;
    private String order_address;
    private String user_phone;
    private Integer order_state;
    private Date visit_date;

   
}
