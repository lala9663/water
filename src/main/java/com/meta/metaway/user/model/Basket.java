package com.meta.metaway.user.model;

import lombok.Data;

@Data
public class Basket {
	    private Long userId;
	    private Long productId;
	    private Long contractId;
	    private int productPcs;
}
