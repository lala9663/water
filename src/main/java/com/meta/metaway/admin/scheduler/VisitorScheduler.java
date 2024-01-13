package com.meta.metaway.admin.scheduler;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.meta.metaway.admin.dao.IAdminRepository;
import com.meta.metaway.admin.model.Visitor;
import com.meta.metaway.admin.service.AdminService;
import com.meta.metaway.admin.service.IAdminService;

@Component
public class VisitorScheduler {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IAdminRepository adminRepository;

    // 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void resetDailyVisitorCount() {
        // 방문자 수 초기화
        String key = AdminService.DAILY_VISITOR_COUNT_KEY + LocalDate.now();
        adminService.resetDailyVisitorCount(key);    
    }
    
    
    // 매일 11시 59분에 실행
    @Scheduled(cron = "0 59 11 * * *")
    public void saveDailyVisitorCountToDatabase() {

    	long dailyVisitorCount = adminService.getDailyVisitorCount();
        long visitorId = adminRepository.selectNextVisitorId();

        Visitor visitor = new Visitor();
        visitor.setVisitorId(visitorId);
        visitor.setVisitDate(LocalDate.now());
        visitor.setVisitorCount(dailyVisitorCount);

        adminService.insertViewCount(visitor);
    }

    
}