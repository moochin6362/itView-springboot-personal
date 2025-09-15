package itView.springboot.common.config;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import itView.springboot.mapper.AdminMapper;
import itView.springboot.service.AdminService;
import itView.springboot.service.InquiryService;
import itView.springboot.service.ProductService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableScheduling
@Component
@RequiredArgsConstructor
public class SchedulerConfig {

	 
}
