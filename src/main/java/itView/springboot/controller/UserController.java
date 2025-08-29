package itView.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itView.springboot.exception.UserException;
import itView.springboot.service.UserService;
import itView.springboot.vo.User;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    //비밀번호 찾기
    @GetMapping("/findPwd")
    public String findPwdPage() {
    	return "/login/find_password";
    }
    
    //비밀번호 재설정(update)
    @PostMapping("/resetPwd")
    public String resetPwd(
    		Model model,
    		@ModelAttribute User u
    		) {
    	u.setUserPassword(u.getUserPassword()); //비번 암호화
    	
    	int result = userService.resetPwd(u);
    	if(result > 0) {
    		model.addAttribute("msg", "비밀번호 수정이 완료되었습니다.");
    		model.addAttribute("url", "/home");
    		return "redirect:/views/common/sendRedirect";
    	} else {
    		throw new UserException("비밀번호 수정을 실패하였습니다.");
    	}
    	
    }
}
