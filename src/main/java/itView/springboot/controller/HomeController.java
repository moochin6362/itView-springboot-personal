package itView.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
    	 System.out.println("홈 컨트롤러 호출됨");
    	 System.out.println("뷰 경로: " + "index"); // 확인
        return "index"; // templates/views/index.html
    }
}
