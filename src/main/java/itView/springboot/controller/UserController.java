package itView.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import itView.springboot.service.UserService;
import itView.springboot.vo.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    // 회원가입 페이지
    @GetMapping("/signUp")
    public String signUpPage() {
        return "login/signUp";
    }


}
