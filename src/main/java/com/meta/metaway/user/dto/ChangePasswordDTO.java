package com.meta.metaway.user.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private Long id;
    private String oldPassword;
    private String newPassword;
}
