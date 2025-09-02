package itView.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import itView.springboot.exception.UserException;
import itView.springboot.service.UserService;
import itView.springboot.vo.User;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BCryptPasswordEncoder bcrypt;
    
    
    //아이디 찾기
    @GetMapping("/findId")
    public String findIdPage() {
    	return "/login/findId";
    }
    
    @PostMapping("/findId")
    @ResponseBody
    public Map<String, String> findId(
    		@RequestParam("userType") String userType,
    		@RequestParam("email") String email,
    		@RequestParam("userPassword")String userPassword) {
    	Map<String, String> result = new HashMap<String, String>(); //userId만 map에 담아서 반환..하는건데
    	
    	User u = userService.findId(email,userType);
    	if(u != null && bcrypt.matches(userPassword, u.getUserPassword())) {
    		result.put("userId", u.getUserId());
    	} else {
    		result.put("userId", null);
    	}
    	
    	return result;
    }
    
    
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
