package itView.springboot.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import itView.springboot.common.config.interceptor.CheckAdminInterceptor;
import itView.springboot.common.config.interceptor.FlashMessageInterceptor;
import itView.springboot.common.config.interceptor.LogInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	// 기본 정적 리소스
        registry.addResourceHandler("/**")
                .addResourceLocations(
                		"file:///C:/uploadFilesFinal/product/"
                		, "classpath:/static/");

        // temp 폴더 (수정 금지)
        registry.addResourceHandler("/uploadFilesFinal/temp/**")
                .addResourceLocations("file:///c:/uploadFilesFinal/temp/");

        // notice 폴더 (수정 금지)
        registry.addResourceHandler("/uploadFilesFinal/notice/**")
                .addResourceLocations("file:///c:/uploadFilesFinal/notice/");

        // community 폴더 (수정 금지)
        registry.addResourceHandler("/uploadFilesFinal/community/**")
                .addResourceLocations("file:///c:/uploadFilesFinal/community/");

    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 수정/삭제 후 Flash 메시지 처리
        registry.addInterceptor(new FlashMessageInterceptor())
                .addPathPatterns("/**"); // 전체 경로 적용

        // 관리자 접근 체크
        registry.addInterceptor(new CheckAdminInterceptor())
                .addPathPatterns("/inhoAdmin/**", "/admin/**")
                .excludePathPatterns("/inhoAdmin/ranking", "/inhoAdmin/enrollReport", "/inhoAdmin/enrollProductReport");// ranking page 제외
        
        // 로그 인터셉터
        registry.addInterceptor(new LogInterceptor())
		.addPathPatterns("/inhoAdmin/**");
    }
}
