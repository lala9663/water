package com.meta.metaway.staff.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.meta.metaway.jwt.JWTUtil;
import com.meta.metaway.multiClass.MultiClass;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.staff.dto.StaffListDTO;
import com.meta.metaway.staff.model.Staff;
import com.meta.metaway.staff.service.IStaffService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/staff")
public class StaffController {
	
	@Autowired
    private JWTUtil jwtUtil;
	@Autowired
	private IStaffService staffService;
	@Autowired
	private MultiClass multiClass;

	
//	@PostMapping("/workplace")
//	public ResponseEntity<String> createWorkPlace(HttpServletRequest request, @RequestBody Staff staff) {
//		String token = multiClass.getToken(request);
//	    String username = jwtUtil.getUsername(token);
//	    if (!username.isEmpty()) {
//	        try {
//	            staffService.createWorkPlace(username, staff.getWorkPlace());
//	            return ResponseEntity.ok("새로운 근무지가 생성되었습니다.");
//	        } catch (Exception e) {
//	            return ResponseEntity.badRequest().body("근무지 생성에 실패했습니다: " + e.getMessage());
//	        }
//	    } else {
//	        return ResponseEntity.badRequest().body("유저네임이 식별되지 않았습니다.");
//	    }
//	}

//    @PutMapping("/update-workplace")
//    public ResponseEntity<String> updateWorkPlace(HttpServletRequest request, @RequestBody Staff staff) {
//		String token = multiClass.getToken(request);
//        String username = jwtUtil.getUsername(token);
//        if (!username.isEmpty()) {
//            try {
//                staffService.updateWorkPlace(username, staff);
//                return ResponseEntity.ok("근무지 정보가 업데이트되었습니다.");
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body("근무지 정보 업데이트에 실패했습니다: " + e.getMessage());
//            }
//        } else {
//            return ResponseEntity.badRequest().body("유저네임이 식별되지 않았습니다.");
//        }
//    }	
    
  //staff 기사 회원 주문 목록 리스트
    @GetMapping("/drive/list")
    public String getOrderProductList( HttpSession session, Model model) {
        // 주문 상품 목록 조회
        List<StaffListDTO> orderProductList = staffService.getOrderProductList();

        model.addAttribute("staffList", orderProductList);
        return "staff/drivemain";
    }

    //staff 코디 회원 주문 목록 리스트
    @GetMapping("/cody/list")
    public String getCodyProductList( HttpSession session, Model model) {
        // 주문 상품 목록 조회
        List<StaffListDTO> orderProductList = staffService.getOrderProductList();

        model.addAttribute("staffList", orderProductList);
        return "staff/codymain";
    }
    
    @GetMapping("/createWorkPlace")
    public String showCreateWorkPlaceForm(HttpServletRequest request, Model model) {
		String token = multiClass.getToken(request);
		if (token != null) {
			long userId = jwtUtil.getId(token);
			String account = jwtUtil.getUsername(token);
			model.addAttribute("account", account);

		}
        return "staff/createWorkPlace";
    }

    @PostMapping("/createWorkPlace")
    public String createWorkPlace(HttpServletRequest request, @RequestParam String workPlace) {
		String token = multiClass.getToken(request);
		long userId = jwtUtil.getId(token);
		
    	staffService.createWorkPlace(userId, workPlace);
    	
    	return "redirect:/user/profile"; 
	}
    
    @GetMapping("/updateWorkPlace")
    public String showUpdateWorkPlaceForm(HttpServletRequest request, Model model) {
        String token = multiClass.getToken(request);
        if (token != null) {
            long userId = jwtUtil.getId(token);
            String account = jwtUtil.getUsername(token);

            String currentWorkPlace = staffService.getCurrentWorkPlace(userId);

            model.addAttribute("account", account);
            model.addAttribute("currentWorkPlace", currentWorkPlace);
        }

        return "staff/updateWorkPlace";
    }


    @PostMapping("/updateWorkPlace")
    public String updateWorkPlace(HttpServletRequest request, @RequestParam String workPlace) {
		String token = multiClass.getToken(request);
		long userId = jwtUtil.getId(token);
		
    	staffService.updateWorkPlace(userId, workPlace);
    	
    	return "redirect:/user/profile"; 
	}

}

	    
    
