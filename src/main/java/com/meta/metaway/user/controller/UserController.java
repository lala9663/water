package com.meta.metaway.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meta.metaway.jwt.JWTUtil;
import com.meta.metaway.multiClass.MultiClass;
import com.meta.metaway.order.model.Order;
import com.meta.metaway.order.model.OrderDetail;
import com.meta.metaway.user.model.Basket;
import com.meta.metaway.user.model.User;
import com.meta.metaway.user.service.IUserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
    private IUserService userService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private MultiClass multiClass;
	

    @GetMapping("/profile")
    public String getProfilePage(HttpServletRequest request, Model model) {
        String token = multiClass.getToken(request);

        if (token != null) {
            String username = jwtUtil.getUsername(token);
            User user = userService.getUserByUsername(username);

            if (user != null) {
                model.addAttribute("userProfile", user);
                return "user/profile";
            }
        }

        return "redirect:/login";
    }

    @GetMapping("/update")
    public String getUpdateProfilePage(HttpServletRequest request, Model model) {
        String token = multiClass.getToken(request);
        String redirectUrl = "redirect:/login"; 

        if (token != null) {
            String username = jwtUtil.getUsername(token);
            User userProfile = userService.getUserByUsername(username);

            if (userProfile != null) {
                model.addAttribute("userProfile", userProfile);
                redirectUrl = "user/updateProfile";
            }
        }

        return redirectUrl;
    }

	    @PostMapping("/update")
	    public String updateUser(HttpServletRequest request, @ModelAttribute("userProfile") User updatedUser) {
	        String token = multiClass.getToken(request);
	
	        if (token != null) {
	            String username = jwtUtil.getUsername(token);
	            System.out.println("회원 수정에서 유저네임: " + username);
	            userService.updateUser(username, updatedUser);
	        }
	
	        return "redirect:/user/profile"; 
	    }
	    
	    @PostMapping("/changepassword")
	    public String updateUserPassword(HttpServletRequest request,@ModelAttribute("updatePassowrd") User user) {
	        String token = multiClass.getToken(request);
	        
	        String account = jwtUtil.getUsername(token);
	        System.out.println("패스워드변경 계정: " + account);
	        
	        userService.updateUserPassword(account, user);
	        
	        return "redirect:/user/profile";
	           
	    }
	    
	    @GetMapping("/changepassword")
	    public String userProfile(HttpServletRequest request, Model model) {
	        String token = multiClass.getToken(request);
	        String redirectUrl = "redirect:/login";        

	        if (token != null) {
	            String username = jwtUtil.getUsername(token);
	            User userProfile = userService.getUserByUsername(username);
	            
	            if (userProfile != null) {
	                model.addAttribute("userProfile", userProfile);
	                redirectUrl = "user/updateProfile";
	            }
	        }

	        	return redirectUrl;
	    }
	    
	    @GetMapping("/delete")
	    public String getdeleteUser(HttpServletRequest request) {
	        String token = multiClass.getToken(request);
	        
	        if (token != null) {
		    	return "user/delete";

	        }
	    	return "user/delete";
	    }
	    
	    @PostMapping("/delete")
	    public String deleteUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("deleteUser") User deleteUser,
	    		@RequestParam("password") String password, Model model) {
	        String token = multiClass.getToken(request);
	        String redirectUrl = "redirect:/user/profile";
	        
	        if (token != null) {
	            Long id = jwtUtil.getId(token);
	            
	            boolean passwordMatches = userService.checkPassword(id, password);
	            if (passwordMatches) {
	                userService.deleteUserById(id);
	          
	                model.addAttribute("success", true);

	                Cookie deleteCookie = new Cookie("token", null);
	                deleteCookie.setMaxAge(0); 
	                deleteCookie.setHttpOnly(true); 
	                deleteCookie.setPath("/"); 
	                response.addCookie(deleteCookie);
	                
	    	        HttpSession session = request.getSession(false);
	    	        if (session != null) {
	    	            session.invalidate();
	    	        }
	    	        
	            } else {
	                model.addAttribute("failure", true);

	                redirectUrl = "redirect:/user/profile";
	            }
	        }
			return redirectUrl;
	    }
	    
	    @ResponseBody
	    @PostMapping("/basket")
	    public ResponseEntity<?> addProductToBasket(Basket basket, HttpServletRequest request) {
	    	basket.setUserId(multiClass.getTokenUserId(request));
	    	try {
		    	userService.addProductToBasket(basket);
		    	return ResponseEntity.ok().body("장바구니에 담았습니다!");
			} catch (Exception e) {
				 return ResponseEntity.ok().body("이미 장바구니에 담겨있습니다!");
			}
	    }
	    
	    @ResponseBody
	    @PostMapping("/basket/remove")
	    public ResponseEntity<?> removeProductToBasket(Basket basket, HttpServletRequest request) {
	    	basket.setUserId(multiClass.getTokenUserId(request));
	    	try {
	    		userService.removeProductFromBasket(basket);	
				return ResponseEntity.ok().body("삭제가 완료되었습니다");
			} catch (Exception e) {
				return ResponseEntity.ok().body("삭제에 실패했습니다");
			}
	    }
	    
	    @GetMapping("/basket")
	    public String getBasketInfo(HttpServletRequest request, Model model) {
	    	model.addAttribute("product", userService.getBasketItemsByUserId(multiClass.getTokenUserId(request)));
	    	return "user/basket";
	    }
	    
	    @GetMapping("/order")
	    public String getUserOrders(HttpServletRequest request, Model model) {
	    	String token = multiClass.getToken(request);
	    	
	        if (token != null) {
	            Long userId = jwtUtil.getId(token);
	            System.out.println("주문조회에서 유저id : " + userId);
	            
		    	List<Order> orderList = userService.getOrdersByUserId(userId);
		        model.addAttribute("orderList", orderList);
	        }
	        return "user/orderlist"; 
	    }
	    
	    @GetMapping("/orderdetail")
	    public String getUserOrderDetails(HttpServletRequest request, Model model) {
	    	String token = multiClass.getToken(request);
	    	
	        if (token != null) {
	            Long userId = jwtUtil.getId(token);
	            System.out.println("주문상세조회에서 유저id : " + userId);
	            
		    	List<OrderDetail> orderDetailList = userService.getOrderDetailByUserId(userId);
		        model.addAttribute("orderList", orderDetailList);
	        }
	        return "user/orderDetailList"; 
	    }
}
