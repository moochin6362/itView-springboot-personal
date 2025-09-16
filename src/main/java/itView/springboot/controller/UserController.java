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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import itView.springboot.exception.LoginException;
import itView.springboot.exception.UserException;
import itView.springboot.service.UserService;
import itView.springboot.vo.User;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@SessionAttributes("loginUser")
@RequiredArgsConstructor
public class UserController {
//	private final ProductService pService;
    private final UserService userService;
    private final BCryptPasswordEncoder bcrypt;
    
//    // 로그인 페이지             
    @GetMapping("/login")
    public String loginPage() {
        return "login/login";
    }
	
    @PostMapping("login")
    public String login(User u, Model model,
                        @RequestParam("beforeURL") String beforeURL,
                        @RequestParam("userType") String userType) {

        User loginUser = userService.login(u);

        if (loginUser != null && bcrypt.matches(u.getUserPassword(), loginUser.getUserPassword())) {
            
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
                    return "redirect:/inhoAdmin/adminMain";
                default:
                    throw new UserException("알 수 없는 사용자 유형입니다.");
            }
        } else {
            throw new LoginException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }
    
//	@PostMapping("login")
//	public String login(User u, Model model) {
//		//System.out.println(bcrypt.encode("1234"));
//		User loginUser = pService.login(u);
//		
//		if(loginUser != null && bcrypt.matches(u.getUserPassword(), loginUser.getUserPassword())) {
//			model.addAttribute("loginUser", loginUser);
//			if(loginUser.getUserType().equals("U")){
//				return "redirect:/";
//			} else if(loginUser.getUserType().equals("A")) {
//				return "redirect:/inhoAdmin/enrollCouponNotice";
//			} else {
//				return "redirect:/seller/home";
//			}
//		} else {
//			throw new ProductException("로그인을 실패하였습니다.");
//		}
//		
//	}
    
	//로그아웃
	@GetMapping("logout")
	public String logout(SessionStatus status) {
	    status.setComplete();
	    return "redirect:/";
	}	
 
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
