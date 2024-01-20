package com.meta.metaway.returned.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meta.metaway.order.dao.IOrderRepository;
import com.meta.metaway.returned.dao.IReturnedRepository;
import com.meta.metaway.returned.model.Returned;

@ExtendWith(MockitoExtension.class)
class RetrunedServiceTest {
	@Mock
	private IOrderRepository orderRepository;

	@Mock
	private IReturnedRepository returnedRepository;
	@InjectMocks
	private RetrunedService retrunedService;
	
	private Returned returned;
	
	 @BeforeEach
	    void setUp() {
	        returned = new Returned();
	        // 필요한 초기화를 여기에 작성
	    }

	@DisplayName("반납/해지/환불 신청")
	@Test
	public void testInsertReturnTable() {
		returned.setStateType(0); // 예시 값
        returned.setReturnPrice(1000); // 예시 값

        when(returnedRepository.getMaxReturnId()).thenReturn(1L);

        retrunedService.InsertReturnTable(returned);

        // returnId 및 stateType 설정 확인
        assertEquals(1L, returned.getReturnId());
        assertEquals(4, returned.getStateType()); // 예시 상태 변경 확인

        // orderRepository 및 returnedRepository 메서드 호출 확인
        verify(orderRepository, times(1)).updateOrderDetailState(returned);
        verify(returnedRepository, times(1)).InsertReturnTable(returned);
	}
	@DisplayName("해지/환불 신청 취소")
	@Test
	public void testCancelproductReturn() {
		returned.setStateType(3); // 예시 값

        retrunedService.CancelproductReturn(returned);

        // stateType이 업데이트되었는지 검증
        assertEquals(1, returned.getStateType()); // stateType이 3에서 1로 변경되어야 함

        // orderRepository와 returnedRepository의 메서드 호출 검증
        verify(orderRepository, times(1)).updateOrderDetailState(returned);
        verify(returnedRepository, times(1)).CancelproductReturn(returned);
	}

}
