package com.meta.metaway.product.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.meta.metaway.product.model.Product;
import com.meta.metaway.product.model.Contract;

public interface IProductService {
//	상품 등록
	String productFileInsert(Product product, List<MultipartFile> fileList);
//	상품 삭제
	void productDelete(long productId);
//  상품 정보 변경
	String UpdateproductFile(Product product, List<MultipartFile> fileList);
//	상품 기능 조회
	List<String> getProductKey(long productId);
//	상품 전체 조회
	List<Product> getAllProductInfo();
//	상품 정보 조회
	Product getTargetProductInfo(long productId);
//	상품 약정 조회
	List<Contract> getProductContractList(long productId);
}
