package itView.springboot.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // 설정 파일 역할의 클래스를 bean으로 등록
//@RequiredArgsConstructor
//@EnableWebSecurity
public class SecurityConfig {
//   private final JwtFilter jwtFilter;
//    private final CustomAccessDeniedHandler accessDeniedHandler;
//    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
   // private final CustomUserDetailsService customUserDetailsService;
    
    
    @Bean // 반환 값을 bean으로 등록
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    //@Bean
//    public AuthenticationManager authenticationManager(
//          AuthenticationConfiguration configuration
//          ) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//    //@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth          //접근 권한설정
//                        .requestMatchers("/admin/**","/", "/login/**", "/signUp/**", "/review/**","/category/**", "/experience/**","/css/**", "/js/**", "/images/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(exception -> exception
//                        .accessDeniedHandler(accessDeniedHandler)
//                        .authenticationEntryPoint(authenticationEntryPoint)
//                )
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                
//                .build();
//    }
//    
//    //@Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(customUserDetailsService);
//        authProvider.setPasswordEncoder(getPasswordEncoder());
//        return authProvider;
//    }
//
//    
}
