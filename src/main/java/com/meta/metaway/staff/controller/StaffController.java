package com.meta.metaway.staff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.metaway.jwt.JWTUtil;
import com.meta.metaway.product.model.Product;
import com.meta.metaway.staff.model.Staff;
import com.meta.metaway.staff.service.IStaffService;

@RestController
@RequestMapping("/staff")
public class StaffController {
	
	@Autowired
    private JWTUtil jwtUtil;
	@Autowired
	private IStaffService staffService;
	
	@PostMapping("/workplace")
	public ResponseEntity<String> createWorkPlace(@RequestHeader("Authorization") String token, @RequestBody Staff staff) {
	    String username = jwtUtil.getUsername(token);
	    if (!username.isEmpty()) {
	        try {
	            staffService.createWorkPlace(username, staff.getWorkPlace());
	            return ResponseEntity.ok("새로운 근무지가 생성되었습니다.");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("근무지 생성에 실패했습니다: " + e.getMessage());
	        }
	    } else {
	        return ResponseEntity.badRequest().body("유저네임이 식별되지 않았습니다.");
	    }
	}


    @PutMapping("/update-workplace")
    public ResponseEntity<String> updateWorkPlace(@RequestHeader("Authorization") String token, @RequestBody Staff staff) {
        String username = jwtUtil.getUsername(token);
        
        if (!username.isEmpty()) {
            try {
                staffService.updateWorkPlace(username, staff);
                return ResponseEntity.ok("근무지 정보가 업데이트되었습니다.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("근무지 정보 업데이트에 실패했습니다: " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("유저네임이 식별되지 않았습니다.");
        }
    }	
    
  //리스트
    @GetMapping("/visit/list")
    public String getProductForStaff(@RequestHeader(name = "Authorization", required = false) String token, Model model) {
        if (token != null && !token.isEmpty()) {
        	try {
        		String account = jwtUtil.getUsername(token);
        		List<Product> productList = staffService.getProductForStaff(account);
        		model.addAttribute("items", productList);
        		return "staff/staffdriveList"; 
        	}catch(Exception e){
        		e.printStackTrace();
        		return "error";
        	}
        } else {
            return "error";
        }
    }


  
    
}