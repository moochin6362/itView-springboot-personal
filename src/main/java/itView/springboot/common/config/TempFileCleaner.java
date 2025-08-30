package itView.springboot.common.config;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;

@Component
@EnableScheduling
public class TempFileCleaner {

    private static final String TEMP_DIR = "c:/uploadFilesFinal/temp";

    // 매일 자정에 실행 (cron 표현식)
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanOldTempFiles() {
        File folder = new File(TEMP_DIR);
        if (!folder.exists()) return;

        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            // 파일 생성 시간 체크 후 24시간 지난 파일 삭제
            long diff = Instant.now().toEpochMilli() - file.lastModified();
            if (diff > 24 * 60 * 60 * 1000) { // 24시간 = 1일
                file.delete();
            }
        }
    }
}
