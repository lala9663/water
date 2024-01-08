package com.meta.metaway.product.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.meta.metaway.product.dao.IProductRepository;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.product.service.IProductService;

@Controller
public class ProductController {
	
	@Autowired
	IProductService productService; 
//	상품 등록창 가기
	@GetMapping("/product/insert")
	String productInsertForm() {
		return "product/productInsert";
	}
//	상품 전체 조회
	@GetMapping("/product")
	String GetProductAll(Model model) {
		model.addAttribute("productList", productService.getAllProductInfo());
		return "product/product";
	}
//	상품 상세 조회
	@GetMapping("/product/{productId}")
	String productDetail(@PathVariable long productId, Model model) {
		Product product = productService.getTargetProductInfo(productId);
		product.setFunctionList(productService.getProductKey(productId));
		product.setContract(productService.getProductContractList(productId));
		model.addAttribute("product", product);
		return "product/productDetail";
	}
//	상품 업데이트 가기
	@GetMapping("product/update/{productId}")
	String productUpdate(@PathVariable long productId, Model model){
		Product product = productService.getTargetProductInfo(productId);
		product.setFunctionList(productService.getProductKey(productId));
		product.setContract(productService.getProductContractList(productId));
		model.addAttribute("product", product);
		return "product/productUpdate";
	}
//	상품 업데이트
	@PostMapping("product/update/{productId}")
	String productUpdate(Product product, @RequestParam(value="productFile", required = false) List<MultipartFile> fileList) {
		productService.UpdateproductFile(product, fileList);
		return "redirect:/product/{productId}";
	}
//	상품 등록
	@PostMapping("/product/insert")
	String productInsert(@ModelAttribute Product product, @RequestParam(value="productFile", required = false) List<MultipartFile> fileList){
		productService.productFileInsert(product, fileList); 
		return "product/productInsert";
	}
//	상품 제거
	@GetMapping("product/delete/{productId}")
	String productDelete(@PathVariable long productId) {
		productService.productDelete(productId);
		return "redirect:/product";
	}
}
