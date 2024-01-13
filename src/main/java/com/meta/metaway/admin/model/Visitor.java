package com.meta.metaway.admin.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Visitor {
    private Long visitorId;
    private LocalDate visitDate;
    private Long visitorCount;
}
