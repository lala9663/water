package com.meta.metaway.order.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.meta.metaway.multiClass.MultiClass;
import com.meta.metaway.order.model.Order;
import com.meta.metaway.order.service.IOrderService;
import com.meta.metaway.product.model.Contract;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.product.service.IProductService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class OrderController {
	
	
	@Autowired
	IOrderService orderService;
	
	@Autowired
	IProductService productService;
	
	@Autowired
	MultiClass multiClass;
	
	@PostMapping("order")
	String order(Model model, @ModelAttribute Order order, HttpServletRequest request) {
		order.setUserId(multiClass.getTokenUserId(request));
		List<Contract> contractList = new ArrayList<>();
		List<Product> productList = new ArrayList<>();
		int rentalPrice = 0;
		int orderPrice = 0;
		for(Contract contract : order.getContractList()) {
			contract = orderService.GetContractInfo(contract.getContractId(),contract.getProductId());
			contractList.add(contract);
			productList.add(productService.getTargetProductInfo(contract.getProductId()));
			if(contract.getContractYear() == 0) {
				orderPrice += contract.getContractPrice();
			}
			else {
				rentalPrice += contract.getContractPrice();
			}
		}
 		order.setRentalPrice(rentalPrice);
		order.setOrderPrice(orderPrice);
		model.addAttribute("size" , contractList.size() - 1);
		model.addAttribute("order", order);
		model.addAttribute("product",productList);
		model.addAttribute("contract", contractList);
		return "order/order";
	}
	
	
	@PostMapping("order/apply")
	String orderInsert(Model model, @ModelAttribute Order order, HttpServletRequest request) {
		order.setUserId(multiClass.getTokenUserId(request));
		orderService.InsertOrder(order);
		return "redirect:/product";
	}
	
	@PostMapping("order/cancel")
	String cancelOrder(Order order, HttpServletRequest request) { 
		order.setUserId(multiClass.getTokenUserId(request));
		orderService.cancelOrder(order);
		return "redirect:/user/orderdetail";
	}
	
	@PostMapping("order/updateDate")
	String orderUpdateDate(Order order) {
		orderService.orderUpdateDate(order);
		return "redirect:/user/orderdetail";
	}
}
