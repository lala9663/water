package com.meta.metaway.returned.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.metaway.order.dao.IOrderRepository;
import com.meta.metaway.returned.dao.IReturnedRepository;
import com.meta.metaway.returned.model.Returned;

@Service
public class RetrunedService implements IReturnedService {
	
	@Autowired
	IReturnedRepository returnedRepository;
	
	@Autowired
	IOrderRepository orderRepository;

	@Override
	public void InsertReturnTable(Returned returned) {
		
		
		System.out.println(returned.toString());
		try {
			returned.setReturnId(returnedRepository.getMaxReturnId());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if(returned.getStateType() == 0) {
			returned.setStateType(4);
		}
		else if(returned.getStateType() == 1 && returned.getReturnPrice() == 0) {
			returned.setStateType(2);
		}
		else if(returned.getStateType() == 1 && returned.getReturnPrice() != 0) {
			returned.setStateType(3);
		}
		returned.setReturnDate(new Timestamp(System.currentTimeMillis()));
		orderRepository.updateOrderDetailState(returned);
		returnedRepository.InsertReturnTable(returned);
	}

	@Override
	public void CancelproductReturn(Returned returned) {
		if(returned.getStateType() == 3) {
			returned.setStateType(1);
		}
		else if(returned.getStateType() == 4) {
			returned.setStateType(0);
		}
		orderRepository.updateOrderDetailState(returned);
		returnedRepository.CancelproductReturn(returned);
	}

}
