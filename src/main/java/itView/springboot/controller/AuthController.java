package itView.springboot.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import itView.springboot.common.config.JwtTokenProvider;
import itView.springboot.common.config.JwtUtil;
import itView.springboot.service.CustomUserDetailsService;
import itView.springboot.vo.UserPrincipal;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;  // JWT 발급

    // 로그인 페이지
    @GetMapping("login")
    public String loginPage() {
        return "login/login";
    }

    // 로그인 처리
    @PostMapping("login")
    public String loginPage(
            @RequestParam("userId") String userId,
            @RequestParam("userPwd") String userPwd,
            HttpServletResponse response,
            Model model
    ) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userId, userPwd)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
            String jwt = jwtUtil.generateToken(principal);

            Cookie cookie = new Cookie("JWT_TOKEN", jwt);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60); // 1시간
            response.addCookie(cookie);

            return "redirect:/"; // 로그인 성공
        } catch (Exception e) {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login/login"; // 로그인 실패
        }
    }

    
    //kakao로그인
    @PostMapping("/kakaoLogin")
    @ResponseBody
    public ResponseEntity<Map<String, String>> kakaoLogin(@RequestParam(name = "accessToken") String accessToken) {
        try {
            String token = customUserDetailsService.loginWithKakao(accessToken);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Map.of("error", e.getMessage()));
        }
    }

    	
 
    
    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        SecurityContextHolder.clearContext();
        return "redirect:/login/login";
    }
}
