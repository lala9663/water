package com.meta.metaway.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.meta.metaway.product.model.Contract;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.product.model.UploadFile;
import com.meta.metaway.product.model.Function;

@Mapper
@Repository
public interface IProductRepository {
//	상품 등록
	void productInsert(Product product);
//	상품 파일 등록
	void productFileInsert(UploadFile uploadFile);
//	상품 약정등록
	void productContractInsert(Contract contract);
//	상품 기능 등록
	void productFunctionInsert(Function function);
//	상품 제거
	void productDelete(long productId);
	
//	상품 기능 조회
	List<String> getProductKey(long productId);
//	상품 전체 조회
	List<Product> getAllProductInfo();
//	상품 정보 조회
	Product getTargetProductInfo(long productId);
//	상품 약정 조회
	List<Contract> getProductContractList(long productId);

//	시퀀스대용
	int getNextMaxProductId();
	int getNextMaxFileId();
	int getNextMaxContractId();
}
