package com.meta.metaway.product.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.meta.metaway.product.dao.IProductRepository;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.product.model.UploadFile;
import com.meta.metaway.product.model.Function;
import com.meta.metaway.product.model.Contract;

@Service
public class ProductService implements IProductService {

	@Autowired
	IProductRepository productRepository;

	@Transactional
	@Override
	public String productFileInsert(Product product, List<MultipartFile> fileList) {
		product.setProductId(productRepository.getNextMaxProductId());
		List<Contract> contractList = product.getContract();
		productRepository.productInsert(product);
		
		if(!product.getFunction().isEmpty()) {
			productFuntionInsert(product);
		}
		if(!contractList.isEmpty()){
			productContractInsert(product, contractList);
		}
		if (!fileList.get(0).getOriginalFilename().equals("") || !fileList.get(0).getOriginalFilename().equals(null)) {
			productFileUpload(product, fileList);
		}
		return "상품 등록 성공";
	}
	
	@Transactional
	@Override
	public String UpdateproductFile(Product product, List<MultipartFile> fileList) {
		List<Contract> contractList = product.getContract();
		productRepository.UpdateProductInfo(product);
		
		if(!product.getFunction().isEmpty()) {
			productRepository.DeleteProductFunction(product.getProductId());
			productFuntionInsert(product);
		}
		if(!contractList.isEmpty()) {
			productRepository.DeleteProductContract(product.getProductId());
			productContractInsert(product, contractList);
		}
		if (!fileList.get(0).getOriginalFilename().equals("") || !fileList.get(0).getOriginalFilename().equals(null)) {
			productRepository.DeleteProductFile(product.getProductId());
			productFileUpload(product, fileList);
		}
		return "상품 업데이트 성공";
	}

	@Override
	public void productDelete(long productId) {
		productRepository.productDelete(productId);
	}

	@Override
	public List<String> getProductKey(long productId) {
		return productRepository.getProductKey(productId);
	}

	@Override
	public Product getTargetProductInfo(long productId) {
		Product product = productRepository.getTargetProductInfo(productId);
		product.setImageFile(productImageString(product.getImageFile()));
		product.setFunctionList(productRepository.getProductKey(product.getProductId()));
		return product;
	}
	
	@Override
	public List<Product> getAllProductInfo() {
		List<Product> AllProduct = productRepository.getAllProductInfo();
		for(Product product : AllProduct) {
			product.setImageFile(productImageString(product.getImageFile()));
			product.setFunctionList(productRepository.getProductKey(product.getProductId()));
			product.setContract(productRepository.getProductContractList(product.getProductId()));
		}
		return AllProduct;
	}

	@Override
	public List<Contract> getProductContractList(long productId) {
		return productRepository.getProductContractList(productId);
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
			uploadFile.setFileType(file.getContentType());
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
	
	
	
	private void productFuntionInsert(Product product) {
		Function function = new Function();
		function.setProductId(product.getProductId());
		for(Short functionNum : product.getFunction()) {
			function.setFunctionId(functionNum);
			productRepository.productFunctionInsert(function);
		}
	}
	private String productImageString(String filePath) {
		try {
			InputStream input = new FileInputStream(filePath);
		
		byte[] byteFile = input.readAllBytes();
		String encodedByte = Base64.getEncoder().encodeToString(byteFile);
		return encodedByte;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
