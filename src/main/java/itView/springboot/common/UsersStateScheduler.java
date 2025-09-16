package itView.springboot.common;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import itView.springboot.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsersStateScheduler {

    private final AdminMapper mapper;

    // 매일 자정 실행
    @Scheduled(cron = "0 20 21 * * *")
    public void activateUsers() {
        LocalDate today = LocalDate.now();
        mapper.restoreUser(today);
    }
}
