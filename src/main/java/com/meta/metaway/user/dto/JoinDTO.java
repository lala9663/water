package com.meta.metaway.user.dto;

import lombok.Data;

@Data
public class JoinDTO {

	private Long userId;
    private String account;
    private String password;
    private String email;
    private String name;
    private String phone;
    private int age;
    private String address;
}
