package com.meta.metaway.user.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meta.metaway.global.MultiClass;
import com.meta.metaway.jwt.JWTUtil;
import com.meta.metaway.order.model.Order;
import com.meta.metaway.staff.service.IStaffService;
import com.meta.metaway.user.dto.EmailRequestDTO;
import com.meta.metaway.user.model.Basket;
import com.meta.metaway.user.model.User;
import com.meta.metaway.user.service.IUserService;
import com.meta.metaway.user.service.MailSendService;

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

	@Autowired
	private MailSendService mailService;
	
	@Autowired
	private IStaffService staffService;

	@GetMapping("/profile")
	public String getProfilePage(HttpServletRequest request, Model model) {
	    String token = multiClass.getToken(request);

	    if (token != null) {
	        long userId = jwtUtil.getId(token);
	        String username = jwtUtil.getUsername(token);
	        User user = userService.getUserByUsername(username);
	        String workPlace = staffService.getCurrentWorkPlace(userId);
	        String role = jwtUtil.getRole(token);
	        System.out.println("권한: " + role );
	        model.addAttribute("userProfile", user);

	        if (workPlace == null && (role.contains("CODI") || role.contains("DRIVER"))) {
	            return "redirect:/user/createWorkPlace";
	        } else {
	            return "user/profile";
	        }
	    }

	    return "redirect:/login";
	}

	@GetMapping("/createWorkPlace")
	public String getProfileInputPage(HttpServletRequest request, Model model) {
	    String token = multiClass.getToken(request);
	    long userId = jwtUtil.getId(token);
        String account = jwtUtil.getUsername(token);
        model.addAttribute("account", account);

        
		return "user/createWorkPlace";
	}

	@PostMapping("/createWorkPlace")
	public String handleProfileInput(@RequestParam("workPlace") String workPlace, HttpServletRequest request) {
	    String token = multiClass.getToken(request);

	    if (token != null) {
	        long userId = jwtUtil.getId(token);
	        staffService.createWorkPlace(userId, workPlace);
	        return "redirect:/user/profile";
	    }

	    return "redirect:/login";
	}

	
	@PostMapping("/saveWorkPlace")
	@ResponseBody
	public ResponseEntity<String> saveWorkPlace(HttpServletRequest request, @RequestBody Map<String, String> payload) {
	    String token = multiClass.getToken(request);
	    long userId = jwtUtil.getId(token);
		String workPlace = payload.get("workPlace");
		System.out.println("저장 근무지: " + workPlace);

	    staffService.createWorkPlace(userId, workPlace);

	    return ResponseEntity.ok("Workplace saved successfully");
	}

	// url 미적용
	@GetMapping("/profileList")
	@ResponseBody
	public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
		try {
			String token = multiClass.getToken(request);

			if (token != null) {
				String username = jwtUtil.getUsername(token);
				User user = userService.getUserByUsername(username);

				if (user != null) {
					return new ResponseEntity<>(user, HttpStatus.OK);
				}
			}

			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			// Handle exceptions and return a JSON response with error details
			return new ResponseEntity<>("Error fetching user info: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
	public String updateUserPassword(HttpServletRequest request, @ModelAttribute("updatePassowrd") User user) {
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
	public String deleteUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("deleteUser") User deleteUser, @RequestParam("password") String password, Model model) {
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
			return ResponseEntity.badRequest().body("이미 장바구니에 담겨있습니다!");
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
			return ResponseEntity.badRequest().body("삭제 실패");
		}
	}

	@GetMapping("/basket")
	public String getBasketInfo(HttpServletRequest request, Model model) {
		model.addAttribute("product", userService.getBasketItemsByUserId(multiClass.getTokenUserId(request)));
		return "user/basket";
	}

	@GetMapping("/orderDetail")
	public String getUserOrderDetails(HttpServletRequest request, Model model, @ModelAttribute Order order) {
		order = userService.getUserMyOrderDetail(order);
		model.addAttribute("order", order);
		model.addAttribute("today", LocalDate.now());
		return "user/orderdetail";
	}

	@GetMapping("/order")
	public String getUserMyOrder(HttpServletRequest request, Model model) {
		try {
			long userId = multiClass.getTokenUserId(request);
			List<Order> orderList = userService.getUserMyOrder(userId);
			model.addAttribute("orderList", orderList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "user/order";
	}

	@GetMapping("/orderList")
	@ResponseBody
	public List<Order> getUserOrderList(HttpServletRequest request) {
		try {
			long userId = multiClass.getTokenUserId(request);
			List<Order> orderList = userService.getUserMyOrder(userId);
			return orderList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@PostMapping("/mailSend")
	@ResponseBody
	@CrossOrigin
	public String mailSend(@RequestBody EmailRequestDTO emailDto, HttpSession session) {
		System.out.println("이메일 인증 이메일 :" + emailDto.getEmail());

		return mailService.joinEmail(emailDto.getEmail(), session);
	}

	@PostMapping("/verifyCode")
	@ResponseBody
	public ResponseEntity<String> verifyCode(@RequestParam String usercode, HttpSession session) {

		if (mailService.verifyCode(usercode, session)) {
			return ResponseEntity.ok("Verification successful!");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed. Please try again.");
		}
	}

}