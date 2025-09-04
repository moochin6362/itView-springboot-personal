package itView.springboot.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import itView.springboot.common.Pagination;
import itView.springboot.dto.UserReport;
import itView.springboot.service.AdminService;
import itView.springboot.vo.PageInfo;
import itView.springboot.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adService;
	
	//관리자 회원조회 list가져오기
	@GetMapping("/searchUser")
	public String userList(
            @RequestParam(value = "page", defaultValue = "1") int currentPage,
            @RequestParam(value = "value", required = false) String value,
            @RequestParam(value = "condition", defaultValue = "all") String condition,
            Model model,
            HttpServletRequest request) {
		//이 요청을 한 사람이 관리자가 맞는지 확인하는 것
		//회원 list가져오기 (검색)

        int listCount = adService.getUserListCount(1, value, condition);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<User> userList = adService.selectUserList(pi, value, condition);

        model.addAttribute("userList", userList);
        model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());
        model.addAttribute("value", value);
        model.addAttribute("condition", condition);

        return "admin/admin_searchUser";
    }
	
	//게시판 상세조회 : 관리자만 볼 수 있는 게시판
	@GetMapping("/userDetail")
	public String userDetailPage(
			@RequestParam("userNo")int userNo,
			@RequestParam(value="page", defaultValue="1") int page,
			Model model
			) {
		UserReport user = adService.selectUser(userNo);
		model.addAttribute("user", user);
		model.addAttribute("page",page);
		
		return "admin/admin_searchUser_detail";
		
	}
	
	
	//관리자 신고게시판 :신고받은 회원 list 가져오기 (1회 이상 && report_status = 'Y')
	@GetMapping("/report")
	public String reportList(
		@RequestParam(value = "page", defaultValue = "1") int currentPage,
        @RequestParam(value = "value", required = false) String value,
        @RequestParam(value = "condition", defaultValue = "all") String condition,
        Model model,
        HttpServletRequest request) {
	
		int listCount = adService.getReportListCount(1, value, condition);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<User> reportList = adService.selecReportList(pi, value, condition);

        model.addAttribute("reportList", reportList);
        model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());
        model.addAttribute("value", value);
        model.addAttribute("condition", condition);

	
        return "admin/admin_report_board";
	}
	

	//신고 게시판 상세조회 : 관리자만 볼 수 있는 게시판
	@GetMapping("/reportDetail")
	public String reportDetailPage(
			@RequestParam("userNo")int userNo,
			@RequestParam(value="page", defaultValue="1") int page,
			Model model
			) {
		UserReport user = adService.selectReport(userNo);
		model.addAttribute("user", user);
		model.addAttribute("page",page);
		
		return "admin/admin_report_detail";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//관리자 일반문의게시판 이동
	@GetMapping("/gBoard")
	public String gBoardPage() {
		return"admin/admin_general_board";
	}
	
	
	//관리자 일반문의게시판 이동
	@GetMapping("/pBoard")
	public String pBoardPage() {
		return"admin/admin_partner_board";
	}
		
	
	
	
	
	
	
	
	
}
