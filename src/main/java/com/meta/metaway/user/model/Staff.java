package com.meta.metaway.user.model;

import lombok.Data;

@Data
public class Staff {
    private Long staffId;
    private Long userId;
    private String workPlace;
    private String authorities;
}
