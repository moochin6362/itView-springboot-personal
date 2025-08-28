package itView.springboot.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.annotation.RequestParam;

import itView.springboot.exception.LoginException;
import itView.springboot.exception.ProductException;
import itView.springboot.service.ProductService;
import itView.springboot.vo.User;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping({"/product", "/login", "/seller"})
@RequiredArgsConstructor
@SessionAttributes("loginUser")
public class ProductController {
	
	private final ProductService pService;
	private final BCryptPasswordEncoder bcrypt;
	
    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "login/login";
    }
	
    @PostMapping("login")
    public String login(User u, Model model,
                        @RequestParam("beforeURL") String beforeURL,
                        @RequestParam("userType") String userType) {

        User loginUser = pService.login(u);

        if (loginUser != null && bcrypt.matches(u.getUserPassword(), loginUser.getUserPassword())) {
            
            // userType => 로그인 실패
            if (!loginUser.getUserType().equals(userType)) {
                throw new LoginException("해당 유형의 회원이 아닙니다.");
            }

            model.addAttribute("loginUser", loginUser);

            switch (loginUser.getUserType()) {
                case "U":
                    return "redirect:/";
                case "P":
                    return "redirect:/seller/home";
                case "A":
                    return "redirect:/";
                default:
                    throw new ProductException("알 수 없는 사용자 유형입니다.");
            }
        } else {
            throw new LoginException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }

	
	//로그아웃
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}	
	
	
	@GetMapping("home")
	public String sellerPageHome() {
		return "seller/sellerPage";
	}
	
	@GetMapping("insertProductPage")
	public String insertProductPage() {
		return "seller/productInsertPage";
	}
}
