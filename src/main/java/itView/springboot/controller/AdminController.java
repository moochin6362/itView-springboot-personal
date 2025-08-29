package itView.springboot.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import itView.springboot.service.AdminService;
import itView.springboot.vo.User;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adService;
	
	//관리자 회원조회
	@GetMapping("/searchUser")
	public String searchUser(
			@RequestParam(required = false) String condition,
			@RequestParam(required = false) String value,
			Model model) {
		//이 요청을 한 사람이 관리자가 맞는지 확인하는 것
		
		
		//회원 목록 가져오기
		//회원 목록을 Model에 담아서 view에 뿌려주기 -> ModelAndView
		ArrayList<User> user;
		if(condition != null && value != null && !value.isEmpty()) {
			//검색어가 존재할 경우 : 검색어에 맞는 회원 리스트 조회
			switch(condition) {
			case "userId" : 
				user = adService.searchUserById(value);
				break;
			case "userType" : 
				user = adService.searchUserByType(value);
				break;
			case "userStatus" : 
				user = adService.searchUserByStatus(value);
				break;
			case "reportCount" :
				int count = Integer.parseInt(value); //신고글이 0개 일수도있으니까
				user = adService.searchUserByReport(count);
				break;
				default : 
					user = adService.getAllUser(); //기본:전체회원목록 반환하기
				}
				
			} else {
				//검색어가 없을 경우 : 전체회원 조회
				user = adService.getAllUser();
			}
		
		//찾은 결과를 (user타입의 객체배열을 뷰에 전달)
		model.addAttribute("user", user);
		return "admin/admin_searchUser";
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
