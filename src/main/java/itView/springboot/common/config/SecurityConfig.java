package itView.springboot.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import itView.springboot.common.config.handler.CustomAccessDeniedHandler;
import itView.springboot.common.config.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;

@Configuration // 설정 파일 역할의 클래스를 bean으로 등록
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
	private final JwtFilter jwtFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

	 
    @Bean // 반환 값을 bean으로 등록
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth 			//접근 권한설정
                        .requestMatchers("/admin/**", "/notice/write", "/notice/insert", "/notice/updForm", "/notice/update", "/notice/delete").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","SELLER")
                        .requestMatchers("/", "/login/**", "/signUp/**", "/review/**", "/error/**", "/category/**", "/experience/**", "/notice/**","/css/**", "/js/**", "/images/**", "/uploads/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                
                .build();
    }
    
}
