package com.meta.metaway.product.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.meta.metaway.product.model.Product;
import com.meta.metaway.product.service.IProductService;

@Controller
public class ProductController {
	
	@Autowired
	IProductService productService; 
	
	@GetMapping("/product/insert")
	String productInsertForm() {
		return "product/upload";
	}
	@GetMapping("/product")
	String GetProductAll(Model model) {
		model.addAttribute("productList", productService.getAllProductInfo());
		return "product/product";
	}
	
	@GetMapping("/product/{productId}")
	String productDetail(@PathVariable long productId, Model model) {
		Product product = productService.getTargetProductInfo(productId);
		product.setFunctionList(productService.getProductKey(productId));
		product.setContract(productService.getProductContractList(productId));
		model.addAttribute("product", product);
		return "product/productDetail";
	}
	@GetMapping("product/update/{productId}")
	String productUpdate(@PathVariable long productId, Model model){
		Product product = productService.getTargetProductInfo(productId);
		product.setFunctionList(productService.getProductKey(productId));
		product.setContract(productService.getProductContractList(productId));
		return "product/productUpdate";
	}
	
	@PostMapping("/product/insert")
	String productInsert(@ModelAttribute Product product, @RequestParam(value="productFile", required = false) List<MultipartFile> fileList){
		productService.productFileInsert(product, fileList); 
		return "product/upload";
	}
	@GetMapping("product/delete/{productId}")
	String productDelete(@PathVariable long productId) {
		productService.productDelete(productId);
		return "redirect:/product";
	}
}
