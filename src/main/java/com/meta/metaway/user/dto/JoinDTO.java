package com.meta.metaway.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JoinDTO {

	private Long userId;
	
    @NotBlank(message = "계정은 필수 입력 항목입니다.")
    private String account;
    
    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 6, message = "비밀번호는 최소 6글자 이상이어야 합니다.")
    private String password;
    
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 주소를 입력하세요.")    
    private String email;
    
    @NotBlank(message = "이름은 필수 입력 항목입니다.")    
    private String name;
    
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "올바른 전화번호 형식이 아닙니다. (010-1234-5678)")    
    private String phone;
    
    private int age;
    
    private String address;
    private String zipcode;
    private String streetadr;
    private String detailadr;
    
}
