package com.meta.metaway.product.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.meta.metaway.product.model.Product;
import com.meta.metaway.product.model.Contract;

public interface IProductService {
	String productFileInsert(Product product, List<MultipartFile> fileList, List<Contract> contractList);
	void productDelete(String productId);
}
