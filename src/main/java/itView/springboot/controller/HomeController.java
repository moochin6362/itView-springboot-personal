package itView.springboot.controller;

import itView.springboot.service.ProductService;
import itView.springboot.vo.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String home(Model model) {
        List<Product> products = productService.getLatestProducts();
        model.addAttribute("products", products);
        return "index"; // templates/views/index.html
    }
}
