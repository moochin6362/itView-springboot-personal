package itView.springboot.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
}
