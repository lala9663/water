package com.meta.metaway.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.product.model.Contract;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.product.model.UploadFile;
import com.meta.metaway.product.model.contract;

@Mapper
@Repository
public interface IProductRepository {
	void productInsert(Product product);
	void productFileInsert(UploadFile uploadFile);
	void productContractInsert(Contract contract);
	void productDelete(String productId);
	int getNextMaxProductId();
	int getNextMaxFileId();
	int getNextMaxContractId();
}
