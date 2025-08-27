package itView.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my")
public class MyController {

    @GetMapping("/myCoupon")
    public String myCoupon() {
        return "my/myCoupon"; 
    }

    @GetMapping("/myPoint")
    public String myPoint() {
        return "my/myPoint"; 
    }
    
 //  리뷰쓰기 페이지
    @GetMapping("/myReview")
    public String myReview() {
        return "my/myReview"; 
      
    }
    
    
    
    
}
