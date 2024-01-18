package com.meta.metaway.admin.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LogCleanupScheduler {

    private static final String LOG_FOLDER_PATH = "C:/Users/kosa/logback"; 

    // 1주일마다 스케줄링
    @Scheduled(cron = "0 0 0 * * MON")
    public void cleanupLogs() {
        File logFolder = new File(LOG_FOLDER_PATH);
        cleanupFiles(logFolder);
    }

    private void cleanupFiles(File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    boolean deletionResult = file.delete();

                    if (deletionResult) {
                        System.out.println("파일 삭제 성공: " + file.getName());
                    } else {
                        System.out.println("파일 삭제 실패: " + file.getName());
                    }
                } else if (file.isDirectory()) {
                    cleanupFiles(file);
                }
            }
        }
    }
}
