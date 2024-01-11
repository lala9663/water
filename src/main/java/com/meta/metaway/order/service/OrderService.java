package com.meta.metaway.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.metaway.order.dao.IOrderRepository;
import com.meta.metaway.order.model.Order;
import com.meta.metaway.product.model.Contract;
import com.meta.metaway.user.dao.IBasketRepository;

@Service
public class OrderService implements IOrderService{
	
	@Autowired
	IOrderRepository orderRepository;
	
	@Autowired 
	IBasketRepository basketRepository;

	@Override
	public Contract GetContractInfo(long contractId, long productId) {
		return orderRepository.GetContractInfo(contractId, productId);
	}

	@Override
	public void InsertOrder(Order order) {
		long orderId = orderRepository.getNextMaxOrderId();
		order.setOrderId(orderId);
		orderRepository.InsertOrder(order);
		
		long orderDetailNum = orderRepository.getNextMaxOrderDetailId();
		for(Contract contract : order.getContractList()) {
			contract.setOrderId(orderId);
			contract.setOrderDetailId(orderDetailNum);
			if(contract.getContractYear() == 0) {
				System.out.println(contract.getContractYear());
				contract.setStateType(0);
			}
			else {
				contract.setStateType(1);
			}
			orderRepository.InsertOrderDetail(contract);
			orderDetailNum++;
		}
		basketRepository.removeAllProductFromBasket(order.getUserId());
	}
	public void cancelOrder(Order order) {
		orderRepository.cancelOrder(order);
	}

}
