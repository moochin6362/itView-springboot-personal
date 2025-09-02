package itView.springboot.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import itView.springboot.service.ProductService;
import itView.springboot.vo.User;

@Controller
@RequiredArgsConstructor
public class GoController {   // ← 클래스에 @RequestMapping 달지 않습니다!

    private final ProductService pService;
    private final BCryptPasswordEncoder bcrypt;

    /** 로그인 페이지 (GET) */
    @GetMapping({"/go/login", "/login/login"})
    public String loginPage() {
        return "login/login";   // templates/login/login.html
    }

    /** 로그인 처리 (POST) */
    @PostMapping({"/go/login", "/login/login"})
    public String doLogin(User form,
                          HttpServletRequest request,
                          RedirectAttributes ra) {
        User found = pService.login(form);
        if (found == null || !bcrypt.matches(form.getUserPassword(), found.getUserPassword())) {
            ra.addFlashAttribute("msg", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "redirect:/login/login"; // 기존 폼 경로로 되돌림
        }

        // 세션 재발급
        HttpSession old = request.getSession(false);
        if (old != null) old.invalidate();
        HttpSession session = request.getSession(true);

        session.setAttribute("loginUser", found);
        session.setAttribute("userNo", found.getUserNo());
        session.setAttribute("userId", found.getUserId());

        String type = String.valueOf(found.getUserType()).toUpperCase();
        if ("S".equals(type)) {
            return "redirect:/seller/home";
        }
        return "redirect:/my/myPage";
    }

    /** 로그아웃 (POST) */
    @PostMapping({"/go/logout", "/login/logout"})
    public String logout(HttpServletRequest request) {
        HttpSession s = request.getSession(false);
        if (s != null) s.invalidate();
        return "redirect:/";
    }
}
