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
@RequestMapping({"/product"})
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService pService;
	private final BCryptPasswordEncoder bcrypt;
	
	
	
	@GetMapping("home")
	public String sellerPageHome() {
		return "seller/sellerPage";
	}
	
	@GetMapping("insertProductPage")
	public String insertProductPage() {
		return "seller/productInsertPage";
	}
}
