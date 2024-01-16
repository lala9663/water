package com.meta.metaway.returned.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
		
		LocalDateTime now = LocalDateTime.now();
		returned.setReturnId(returnedRepository.getNextMaxReturnId());
		if(returned.getStateType() == 0) {
			returned.setStateType(4);
		}
		else if(returned.getStateType() == 1 && now.isBefore(returned.getReturnDate().toLocalDateTime())) {
			returned.setStateType(3);
		}
		else if(returned.getStateType() == 1 && now.isAfter(returned.getReturnDate().toLocalDateTime())) {
			returned.setStateType(2);
		}
		orderRepository.updateOrderDetailState(returned);
		returnedRepository.InsertReturnTable(returned);
	}

	@Override
	public void CancelproductReturn(Returned returned) {
		orderRepository.updateOrderDetailState(returned);
		returnedRepository.CancelproductReturn(returned);
	}

}
