package itView.springboot.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import itView.springboot.common.config.interceptor.FlashMessageInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	// product
        registry.addResourceHandler("/uploadFilesFinal/product/**")
                .addResourceLocations("file:///C:/uploadFilesFinal/product/");

        // notice
        registry.addResourceHandler("/uploadFilesFinal/notice/**")
                .addResourceLocations("file:///C:/uploadFilesFinal/notice/");

        // community
        registry.addResourceHandler("/uploadFilesFinal/community/**")
                .addResourceLocations("file:///C:/uploadFilesFinal/community/");

        // temp
        registry.addResourceHandler("/uploadFilesFinal/temp/**")
                .addResourceLocations("file:///C:/uploadFilesFinal/temp/");

        // 기본 정적 리소스
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 수정/삭제 후 Flash 메시지 처리
        registry.addInterceptor(new FlashMessageInterceptor())
                .addPathPatterns("/**"); // 전체 경로 적용

//        // 관리자 접근 체크
//        registry.addInterceptor(new CheckAdminInterceptor())
//                .addPathPatterns("/inhoAdmin/**"); // admin 페이지에만 적용
    }
}