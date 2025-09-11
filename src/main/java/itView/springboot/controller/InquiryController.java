package itView.springboot.controller;

import itView.springboot.common.Pagination;
import itView.springboot.service.InquiryService;
import itView.springboot.vo.Board;
import itView.springboot.vo.Inquiry;
import itView.springboot.vo.PageInfo;
import itView.springboot.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    // 리스트 페이지
    @GetMapping("/getFaqList")
    public String getInquiryList(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");

        List<Inquiry> inquirys = new ArrayList<>();

        if (user != null) {
            inquirys = inquiryService.getInquirylist(user.getUserNo());
        }

        model.addAttribute("inquirys", inquirys);

        return "inquiry/inquiry";
    }


    @PostMapping("/insertinquiry")
    public String insertinquiry(@ModelAttribute Inquiry inquiry, HttpSession session) {
        User user = (User)session.getAttribute("loginUser");
        inquiry.setUserNo(user.getUserNo());
        int result = inquiryService.insertinquiry(inquiry);
        return "redirect:/inquiry/getFaqList";
    }

}
