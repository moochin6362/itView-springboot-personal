package itView.springboot.controller;

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

import itView.springboot.common.config.JwtUtil;
import itView.springboot.service.UserService;
import itView.springboot.vo.UserPrincipal;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class UserController {
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
    private final UserService userService;
   

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "login/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(
            @RequestParam("userId") String userId,
            @RequestParam("userPwd") String userPwd,
            HttpServletResponse response,
            Model model
    ) {
        try {
            // 스프링 시큐리티 인증
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userId, userPwd)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            // JWT 발급
            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            String jwt = jwtUtil.generateToken(userPrincipal);

            Cookie cookie = new Cookie("JWT_TOKEN", jwt);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60); // 1시간
            response.addCookie(cookie);

            return "redirect:/"; // 로그인 성공 시 홈으로
        } catch (org.springframework.security.core.AuthenticationException e) {
            model.addAttribute("error", "사용자명 또는 비밀번호가 올바르지 않습니다.");
            return "login/login"; // 로그인 실패 시 다시 로그인 페이지
        }
    }

    // 로그아웃 처리
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

    
    
    // 회원가입 페이지
    @GetMapping("/signUp")
    public String signUpPage() {
        return "login/signUp";
    }

    // DB 로그인 기능 (필요 시)
    // @PostMapping("/dbLogin")
    // public String dbLogin(@RequestParam("userId") String userId,
    //                       @RequestParam("userPwd") String userPwd,
    //                       HttpServletResponse response,
    //                       Model model) {
    //     User loginUser = userService.login(userId, userPwd);
    //     // DB 로그인 처리 로직
    //     return "in
}
