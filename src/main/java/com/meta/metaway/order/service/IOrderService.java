package com.meta.metaway.order.service;

import com.meta.metaway.order.model.Order;
import com.meta.metaway.product.model.Contract;

public interface IOrderService {
//   약정 정보 가져오기
   Contract GetContractInfo(long contractId, long productId);
//   주문 생성
   void InsertOrder(Order order);
//   주문 취소
   void cancelOrder(Order order);
}
