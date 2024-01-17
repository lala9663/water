package com.meta.metaway.admin.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.meta.metaway.admin.dao.IAdminRepository;
import com.meta.metaway.admin.dto.AdminOrderDTO;
import com.meta.metaway.admin.dto.AdminOrderDetailDTO;
import com.meta.metaway.admin.dto.AdminScheduleStaffDTO;
import com.meta.metaway.admin.dto.AdminStaffDTO;
import com.meta.metaway.admin.dto.SoldRankDTO;
import com.meta.metaway.admin.dto.UserCountDTO;
import com.meta.metaway.admin.model.Visitor;
import com.meta.metaway.order.dto.OrderDTO;
import com.meta.metaway.staff.dto.StaffDTO;

@Service
public class AdminService implements IAdminService {
	
	@Autowired
	IAdminRepository adminRepository;
	
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final String DAILY_VISITOR_COUNT_KEY = "daily_visitor_count:";
	
    @Override
	public List<AdminOrderDTO> findAllOrderList(int page) {
		int start =(page-1)*10 +1;
		return adminRepository.findAllOrderList(start, start+9);
	}

	@Override
	public int selectTotalOrdersCount() {
		return adminRepository.selectTotalOrdersCount();
	}

	@Override
	public int selectWaitingOrdersCount() {
		return adminRepository.selectWaitingOrdersCount();
	}

	@Override
	public List<AdminOrderDTO> searchOrderListByKeyword(String keyword, Integer orderState, String orderDate, int page) {
		int start = (page-1)*10 + 1;
		return adminRepository.searchOrderListByKeyword("%"+keyword+"%", orderState, orderDate, start, start+9);
	}

	@Override
	public void updateCancleOrder(long orderId) {
		adminRepository.updateCancleOrder(orderId);
	}

	@Override
	public int selectCompleteOrdersCount() {
		return adminRepository.selectCompleteOrdersCount();
	}

	@Override
	public int getOrderId(long orderId) {
		return adminRepository.getOrderId(orderId);
	}

	@Override
	public AdminOrderDetailDTO selectOneOrderList(long orderId) {
		return adminRepository.selectOneOrderList(orderId);
	}

	@Override
	public void updateCompleteOrder(long orderId) {
		adminRepository.updateCompleteOrder(orderId);
	}

	@Override
	public List<AdminStaffDTO> selectAllCodiList() {
		return adminRepository.selectAllCodiList();
	}

	@Override
	public List<AdminStaffDTO> selectAllDriverList() {
		return adminRepository.selectAllDriverList();
	}
	
	@Override
	public int getOrderIdByStaffId(long staffId) {
		return adminRepository.getOrderIdByStaffId(staffId);
	}

	@Override
	public List<AdminScheduleStaffDTO> selectListScheduleStaff(long orderId) {
		return adminRepository.selectListScheduleStaff(orderId);
	}
	
	@Override
	public void deleteSchedule(long orderId, long staffId) {
		adminRepository.deleteSchedule(orderId, staffId);
//		adminRepository.updateStaffStatus(staffId);
	}

	@Override
	public List<AdminOrderDetailDTO> selectAllOrderList(long orderId) {
		return adminRepository.selectAllOrderList(orderId);
	}
	
	@Override
    public List<SoldRankDTO> getSoldRankProductWithoutImage(int orderState) {
        return adminRepository.getSoldRankProductWithoutImage(orderState);
    }
	
    @Override
    public List<SoldRankDTO> getSoldRankProductWithImage(int orderState) {
        return adminRepository.getSoldRankProductWithImage(orderState);
    }
	
    @Override
    public UserCountDTO getTotalUser() {
        return adminRepository.getTotalUser();
    }
    
    @Override
    public int getTotalSalesCount(int orderState) {
        return adminRepository.getTotalSalesCount(orderState);
    }

    @Override
	public void increaseDailyVisitorCount() {
	        // Redis에서 일일 방문자 수를 증가시킴
	        String key = DAILY_VISITOR_COUNT_KEY + LocalDate.now();
	        redisTemplate.opsForValue().increment(key);
	 }

    @Override
    public long getDailyVisitorCount() {
        // Redis에서 일일 방문자 수 조회
        String key = DAILY_VISITOR_COUNT_KEY + LocalDate.now();
        String count = redisTemplate.opsForValue().get(key);

        return count != null ? Long.parseLong(count) : 0;
    }

    // 스케줄러 실행
    @Override
    public void resetDailyVisitorCount(String key) {
        // Redis에서 일일 방문자 수 초기화
        redisTemplate.delete(key);
    }
    
    @Override
    public Long getVisitorCountByDate(LocalDate visitDate) {
        return adminRepository.getVisitorCountByDate(visitDate);
    }
    
    @Override
    public Double getOverallAverageVisitorCount() {
        return adminRepository.getOverallAverageVisitorCount();
    }
    
    // 스케줄러 실행
    @Override
    public void insertViewCount(Visitor data) {
        adminRepository.insertViewCount(data);
    }
    
    @Override
    public List<AdminOrderDTO> getDashboardOrderList() {
        return adminRepository.dashBoardOrderList();
    }
    
	/*
	 * @Override public List<StaffDTO> getUsersWithCodi() { return
	 * adminRepository.getUsersWithCodi(); }
	 */
    
    @Override
    public List<StaffDTO> getUsersWithCodi() {
        return adminRepository.getUsersWithCodi();
    }
    
    @Override
    public long getTotalOrderPriceMonth() {
    	long totalOrderPrice = adminRepository.getTotalOrderPriceMonth();   	
        
        return totalOrderPrice;
    }
    
    @Override
    public long getTotalRentalPriceMonth() {
    	long totalRentalPrice = adminRepository.getTotalRentalPriceMonth();
    	return totalRentalPrice;
    }

	@Override
	public void deleteReturnSchedule(long orderDetailId, long staffId) {
		adminRepository.deleteReturnSchedule(orderDetailId, staffId);
//		adminRepository.updateStaffStatus(staffId);
	}

	@Override
	public void updateCompleteOrderDetail(long orderDetailId) {
		adminRepository.updateCompleteOrderDetail(orderDetailId);
	}

}


