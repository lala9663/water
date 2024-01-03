package com.meta.metaway.product.controller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
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
import com.meta.metaway.product.model.Function;
import com.meta.metaway.product.dao.IProductRepository;
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
	String productDetail(@PathVariable long productId, Model model) {
		Product product = productService.getTargetProductInfo(productId);
		model.addAttribute("product", product);
		model.addAttribute("form",productService.getTargetProductForm(product.getFormId()));
		model.addAttribute("contract", productService.getProductContractList(productId));
		model.addAttribute("function", productService.getProductKey(productId));
		try {
			model.addAttribute("productImage", productService.getTargetProductFile(productId));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "product/productDetail";
	}
	
	@PostMapping("/product/insert")
	String productInsert(@ModelAttribute Product product, @RequestParam(value="productFile", required = false) List<MultipartFile> fileList){
		productService.productFileInsert(product, fileList); 
		return "product/upload";
	}
	@DeleteMapping("product/delete/{productId}")
	String productDelete(@PathVariable long productId) {
		productService.productDelete(productId);
		return "product/upload";
	}
}
