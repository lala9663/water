package com.meta.metaway.admin.scheduler;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.meta.metaway.admin.service.AdminService;

@Component
public class VisitorScheduler {

    @Autowired
    private AdminService adminService;

    // 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void resetDailyVisitorCount() {
        // 방문자 수 초기화
        String key = AdminService.DAILY_VISITOR_COUNT_KEY + LocalDate.now();
        adminService.resetDailyVisitorCount(key);    }
}