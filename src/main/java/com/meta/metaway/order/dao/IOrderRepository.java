package com.meta.metaway.order.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.meta.metaway.order.model.Order;
import com.meta.metaway.product.model.Contract;

@Mapper
@Repository
public interface IOrderRepository {
//   약정받아오기
   Contract GetContractInfo(@Param("contractId")long contractId, @Param("productId")long productId);
//   주문 생성
   void InsertOrder(Order order);   
//   주문 상세 넣기
   void InsertOrderDetail(Contract contract);
//   주문취소
   void cancelOrder(Order order);
   
   
   
   long getNextMaxOrderId();
   
   long getNextMaxOrderDetailId();
   
}