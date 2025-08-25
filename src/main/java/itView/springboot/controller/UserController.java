package itView.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class UserController {

	@GetMapping("/login")
	public String loginPage() {
	    return "login/login"; 
	}

	@GetMapping("/signUp")
	public String signUpPage() {
	    return "login/signUp"; 
	}
}
