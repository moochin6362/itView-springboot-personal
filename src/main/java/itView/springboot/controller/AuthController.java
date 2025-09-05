package itView.springboot.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    //private final AuthenticationManager authenticationManager;
    //private final JwtUtil jwtUtil;
   // private final CustomUserDetailsService customUserDetailsService;
    //private final JwtTokenProvider jwtTokenProvider;  // JWT 발급

    // 로그인 페이지
//    @GetMapping("/login")
//    public String loginPage() {
//        return "login/login";
//    }

    // 로그인 처리
//    @PostMapping("/login")
//    public String login(
//            @RequestParam("userId") String userId,
//            @RequestParam("userPassword") String userPassword,
//            HttpServletResponse response,
//            Model model
//    ) {
//       System.out.println("로그인 시도");
//        try {
//            Authentication auth = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userId, userPassword)
//            );
//            SecurityContextHolder.getContext().setAuthentication(auth);
//
//            UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
//            System.out.println("로그인 성공");
//            
//            //Jwt발급
//            String jwt = jwtUtil.generateToken(principal);
//            Cookie cookie = new Cookie("JWT_TOKEN", jwt);
//            cookie.setHttpOnly(true);
//            cookie.setPath("/");
//            cookie.setMaxAge(60 * 60); // 1시간
//            response.addCookie(cookie);
//
//            // UserType에 따라 경로 분기
//            String userType = principal.getUser().getUserType();
//            System.out.println("userType = '" + userType + "'");
//            if ("A".equalsIgnoreCase(userType)) {
//                return "redirect:/home";  // 관리자
//            } else if ("P".equalsIgnoreCase(userType)) {
//                return "redirect:/seller/sellerPage";  // 파트너
//            } else { 
//                return "redirect:/home";  // 일반 사용자
//            }
//
//        } catch (Exception e) {
//           e.printStackTrace();
//            model.addAttribute("msg", "아이디 또는 비밀번호가 올바르지 않습니다.");
//            System.out.println("로그인 실패");
//            return "/index"; // 로그인 실패
//        }
//    }

    //kakao로그인
//    @PostMapping("/kakaoLogin")
//    @ResponseBody
//    public ResponseEntity<Map<String, String>> kakaoLogin(@RequestParam(name = "accessToken") String accessToken) {
//        try {
//            String token = customUserDetailsService.loginWithKakao(accessToken);
//            return ResponseEntity.ok(Map.of("token", token));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                                 .body(Map.of("error", e.getMessage()));
//        }
//    }

   
    // 로그아웃
//    @GetMapping("/logout")
//    public String logout(HttpServletResponse response) {
//        Cookie cookie = new Cookie("JWT_TOKEN", null);
//        cookie.setPath("/");
//        cookie.setMaxAge(0);
//        cookie.setHttpOnly(true);
//        response.addCookie(cookie);
//        SecurityContextHolder.clearContext();
//        return "redirect:/login/login";
//    }
}
