package com.meta.metaway.product.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.internal.util.Contracts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.meta.metaway.product.dao.IProductRepository;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.product.model.UploadFile;
import com.meta.metaway.product.model.Contract;

@Service
public class ProductService implements IProductService {

	@Autowired
	IProductRepository productRepository;

	@Transactional
	@Override
	public String productFileInsert(Product product, List<MultipartFile> fileList, List<Contract> contractList) {
		product.setProductId(productRepository.getNextMaxProductId());
		productRepository.productInsert(product);
		if(!contractList.isEmpty()){
			productContractInsert(product, contractList);
		}
		if (!fileList.get(0).getOriginalFilename().equals("") || !fileList.get(0).getOriginalFilename().equals(null)) {
			productFileUpload(product, fileList);
		}
		
		return "상품 이미지 등록 성공";
	}

	@Override
	public void productDelete(String productId) {
		productRepository.productDelete(productId);
		
	}
	
	private void productFileUpload(Product product, List<MultipartFile> fileList) {
		UploadFile uploadFile;
		int fileIndex = productRepository.getNextMaxFileId();
		for (MultipartFile file : fileList) {
			uploadFile = new UploadFile();
			uploadFile.setFileId(fileIndex);
			uploadFile.setProductId(product.getProductId());
			uploadFile.setFileOriginalName(file.getOriginalFilename());
			uploadFile.setUuidFileName(UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
			uploadFile.setFilePath("C://upload/" + uploadFile.getUuidFileName());
			uploadFile.setFileSize(file.getSize());
			try {
				file.transferTo(new File(uploadFile.getFilePath()));
				productRepository.productFileInsert(uploadFile);
			} catch (Exception e) {
				throw new RuntimeException("Failed to store file " + uploadFile.getUuidFileName());
			}
			fileIndex++;
		}
	}
	private void productContractInsert(Product product, List<Contract> contractList) {
		int contractIndex = productRepository.getNextMaxContractId();
		for(Contract contract : contractList) {
			contract.setContractId(contractIndex);
			contract.setProductId(product.getProductId());
			productRepository.productContractInsert(contract);
			contractIndex++;
		}
	}
}
