package com.meta.metaway.admin.service;

import java.util.List;

import com.meta.metaway.admin.dto.OrderDTO;

public interface IAdminService {
	List<OrderDTO> getArticleListByPaging(int page);
	int selectTotalOrdersCount();
	int selectWaitingOrdersCount();
	List<OrderDTO> searchOrderListByKeyword(String keyword, Integer orderState, String orderDate, int page);
}
