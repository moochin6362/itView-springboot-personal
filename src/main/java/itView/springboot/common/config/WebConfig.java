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
        registry.addResourceHandler("/uploadFilesFinal/**")
                .addResourceLocations("file:///c:/uploadFilesFinal/notice/");

        // temp 폴더
        registry.addResourceHandler("/uploadFilesFinal/temp/**")
                .addResourceLocations("file:///c:/uploadFilesFinal/temp/");

        // notice 폴더
        registry.addResourceHandler("/uploadFilesFinal/notice/**")
                .addResourceLocations("file:///c:/uploadFilesFinal/notice/");

        // community 폴더
        registry.addResourceHandler("/uploadFilesFinal/community/**")
                .addResourceLocations("file:///c:/uploadFilesFinal/community/");
        // 판매자 관련
        registry.addResourceHandler("/**")
				.addResourceLocations("file:///c:/uploadFilesFinal/product/", "classpath:/static");
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