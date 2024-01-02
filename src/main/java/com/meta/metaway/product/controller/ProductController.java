package com.meta.metaway.product.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.meta.metaway.product.model.Product;
import com.meta.metaway.product.model.Contract;
import com.meta.metaway.product.service.IProductService;

@Controller
public class ProductController {
	
	@Autowired
	IProductService productService; 
	
	@GetMapping("/product/insert")
	String productInsertForm() {
		return "product/upload";
	}
	
	@GetMapping("/product/{productId}")
	String productDetail(@PathVariable String productId) {
		return "product/productDetail";
	}
	
	@PostMapping("/product/insert")
	String productInsert(@ModelAttribute Product product, @RequestParam(value="productFile", required = false) List<MultipartFile> fileList){
		List<Contract> contractList = product.getContract();
		System.out.println(contractList.get(0).getContractPrice());
		productService.productFileInsert(product, fileList, contractList); 
		return "product/upload";
	}
	@DeleteMapping("product/delete/{productId}")
	String productDelete(@PathVariable String productId) {
		productService.productDelete(productId);
		return "product/upload";
	}
}
