package itView.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itView.springboot.service.AdminService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adService;
	
	//관리자 회원조회
	@GetMapping("/searchUser")
	public String searchUser() {
		return "admin/admin_SearchUser";
	}
	
	
	
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
