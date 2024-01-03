package com.meta.metaway.product.service;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.meta.metaway.product.model.Product;
import com.meta.metaway.product.model.Contract;

public interface IProductService {
//	상품 등록
	String productFileInsert(Product product, List<MultipartFile> fileList);
//	상품 삭제
	void productDelete(long productId);
//	상품 기능 조회
	List<String> getProductKey(long productId);
//	상품 정보 조회
	Product getTargetProductInfo(long productId);
//	상품 파일 조회
	String getTargetProductFile(long productId) throws FileNotFoundException;
//	상품 약정 조회
	List<Contract> getProductContractList(long productId);
//	상품 형태 조회
	String getTargetProductForm(int formId);

}
