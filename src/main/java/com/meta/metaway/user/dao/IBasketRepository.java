package com.meta.metaway.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.product.model.Product;
import com.meta.metaway.user.model.Basket;

@Mapper
@Repository
public interface IBasketRepository {

	List<Product> getBasketItemsByUserId(Long userId);
	void addProductToBasket(Basket basket) throws Exception;
	Long getProductIdByContractId(Long contractId);
	void removeProductFromBasket(Basket basket);
	
}
