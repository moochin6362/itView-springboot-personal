package itView.springboot.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itView.springboot.exception.ProductException;
import itView.springboot.service.ProductService;
import itView.springboot.vo.User;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping({"/product", "/login", "/seller"})
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService pService;
	private final BCryptPasswordEncoder bcrypt;
	
	@PostMapping("login")
	public String login(User u, Model model) {
		//System.out.println(bcrypt.encode("1234"));
		User loginUser = pService.login(u);
		
		if(loginUser != null && bcrypt.matches(u.getUserPassword(), loginUser.getUserPassword())) {
			model.addAttribute("loginUser", loginUser);
			if(loginUser.getUserType().equals("U") && loginUser.getUserType().equals("A")) {
				return "redirect:/";
			} else {
				return "redirect:/seller/home";
			}
		} else {
			throw new ProductException("로그인을 실패하였습니다.");
		}
		
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
