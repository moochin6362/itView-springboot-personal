package itView.springboot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import itView.springboot.common.Pagination;
import itView.springboot.service.AdminService;
import itView.springboot.vo.Board;
import itView.springboot.vo.PageInfo;
import itView.springboot.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
//	@GetMapping("/{id}/{page}")
//	public String searchUserDetail(
//			@PathVariable("boardId")int boardId,
//			@PathVariable("page")int page,
//			HttpSession session, Model model
//			) {
//		User loginUser = (User)session.getAttribute("loginUser");
//		Board b = adService.selectBoardList(boardId, loginUser);
//		
//	}
	
	
	
	//관리자 신고게시판 이동
	@GetMapping("/report")
	public String rpBoardPage() {
		return "admin/admin_report_board";
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
