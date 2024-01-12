package com.meta.metaway.returned.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.metaway.order.dao.IOrderRepository;
import com.meta.metaway.returned.dao.IRetunedRepository;
import com.meta.metaway.returned.model.Returned;

@Service
public class RetrunedService implements IReturnedService {
	
	@Autowired
	IRetunedRepository returnedRepository;
	
	@Autowired
	IOrderRepository orderRepository;

	@Override
	public void InsertReturnTable(Returned returned) {
		returned.setReturnId(returnedRepository.getNextMaxReturnId());
		orderRepository.updateOrderDetailState(returned);
		returnedRepository.InsertReturnTable(returned);
	}

	@Override
	public void CancelproductReturn(Returned returned) {
		orderRepository.updateOrderDetailState(returned);
		returnedRepository.CancelproductReturn(returned);
	}

}
