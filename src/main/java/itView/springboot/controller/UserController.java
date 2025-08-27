package itView.springboot.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itView.springboot.service.UserService;
import itView.springboot.vo.User;
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

    
    //비밀번호 찾기
    @GetMapping("/findPwd")
    public String findPwdPage() {
    	return "/login/find_password";
    }
    
    //비밀번호 재설정(update)
    @PostMapping("/resetPwd")
    public String resetPwd(
    		@AuthenticationPrincipal(expression = "user") User loginUser,
    		Model model) {
    	//현재의 loginUser == 요청자? (서비스단에서 검사하기)
    	//비밀번호 업데이트 처리
    	//업데이트 결과 
 //   	int result = userService.resetPwd(loginUser.getUserPassword());
//    	if(result > 0) {
    		model.addAttribute("msg", "비밀번호 수정이 완료되었습니다.");
    		model.addAttribute("url", "/");
    		return "redirect:/views/";
    	}
    	
    }
