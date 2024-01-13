package com.meta.metaway.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String account;
    private String password;
    private String email;
    private String name;
    private String phone;
    private int age;
    private String address;
    private String zipcode;
    private String streetadr;
    private String detailadr;
    private String authorities;
}
