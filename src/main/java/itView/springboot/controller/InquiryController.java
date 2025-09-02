package itView.springboot.controller;

import itView.springboot.common.Pagination;
import itView.springboot.vo.Board;
import itView.springboot.vo.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    // 리스트 페이지
    @GetMapping("/getFaqList")
    public String getFaqList() {

        return "inquiry/Inquiry";
    }

}
