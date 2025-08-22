package itView.springboot.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login/login")
	public String loginPage() {
	    return "views/login/login"; 
	}

	@GetMapping("/login/signUp")
	public String signUpPage() {
	    return "views/login/signUp"; 
	}
}
