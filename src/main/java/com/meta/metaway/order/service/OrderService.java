package com.meta.metaway.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meta.metaway.order.dao.IOrderRepository;
import com.meta.metaway.order.model.Order;
import com.meta.metaway.product.model.Contract;

@Service
public class OrderService implements IOrderService{
	
	@Autowired
	IOrderRepository orderRepository;

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
			orderRepository.InsertOrderDetail(contract);
			System.out.println(contract.toString());
			orderDetailNum++;
		}
	}
	public void cancelOrder(Order order) {
		orderRepository.cancelOrder(order);
	}

	
}
