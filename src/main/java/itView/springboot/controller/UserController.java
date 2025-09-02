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
import jakarta.servlet.http.HttpSession;
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
    
    
    //비밀번호 재설정 페이지 이동
    @GetMapping("/resetPwd")
    public String resetPwd(
    		@RequestParam("userId") String userId,
    		Model model
    		) {
    	model.addAttribute("userId", userId);
    	return "/login/reset_password";
    	
    }
    
    //비번 업뎃시키기
    @PostMapping("/resetPwd")
    public String changePwd(
    		@ModelAttribute User u, Model model) {
    	u.setUserPassword(bcrypt.encode(u.getUserPassword()));
    	int result = userService.updatePassword(u);
    	if(result > 0) {
    		model.addAttribute("msg", "비밀번호가 수정되었습니다.");
    		model.addAttribute("url", "/login/login");
    		return "/common/sendRedirect";
    	} else {
    		throw new UserException("비밀번호 수정을 실패하였습니다.");
    	}
    
    }
    
    
    
}
