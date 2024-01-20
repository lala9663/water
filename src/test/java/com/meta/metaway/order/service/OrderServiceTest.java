package com.meta.metaway.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meta.metaway.order.dao.IOrderRepository;
import com.meta.metaway.order.model.Order;
import com.meta.metaway.product.model.Contract;
import com.meta.metaway.user.dao.IBasketRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
	@Mock
	private IBasketRepository basketRepository;

	@Mock
	private IOrderRepository orderRepository;
	@InjectMocks
	private OrderService orderService;

	
	@DisplayName("약정 정보 확인")
    @Test
    void testGetContractInfo() {
        long contractId = 1L, productId = 1L;
        Contract mockContract = new Contract();
        when(orderRepository.GetContractInfo(contractId, productId)).thenReturn(mockContract);

        Contract result = orderService.GetContractInfo(contractId, productId);

        verify(orderRepository).GetContractInfo(contractId, productId);
        assertEquals(mockContract, result);
    }

	@DisplayName("주문 넣기")
    @Test
    void testInsertOrder() {
        Order order = new Order();
        order.setUserId(1L);
        order.setContractList(Arrays.asList(new Contract()));

        when(orderRepository.getNextMaxOrderId()).thenReturn(1L);
        when(orderRepository.getNextMaxOrderDetailId()).thenReturn(1L);

        orderService.InsertOrder(order);

        verify(orderRepository).getNextMaxOrderId();
        verify(orderRepository).InsertOrder(any(Order.class));
        verify(orderRepository, times(order.getContractList().size())).InsertOrderDetail(any(Contract.class));
    }

	@DisplayName("주문 취소")
    @Test
    void testCancelOrder() {
        Order order = new Order();
        order.setUserId(1L);
        order.setOrderId(1L);

        orderService.cancelOrder(order);

        verify(orderRepository).cancelOrder(order);
    }
	
	@DisplayName("주문 시간 설정")
    @Test
    void testOrderUpdateDate() {
        Order order = new Order();
        order.setOrderId(1L);

        orderService.orderUpdateDate(order);

        verify(orderRepository).orderUpdateDate(order);
    }

}
