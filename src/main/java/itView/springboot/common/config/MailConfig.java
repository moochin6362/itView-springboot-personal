package itView.springboot.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    // Render에서는 실제 메일 전송이 필요 없으므로 더미 Bean 생성
    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl(); // 기본 빈 객체 반환
    }
}
