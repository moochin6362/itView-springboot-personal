package itView.springboot.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import itView.springboot.common.Pagination;
import itView.springboot.dto.ReportDetail;
import itView.springboot.dto.UserReport;
import itView.springboot.dto.prohibitProduct;
import itView.springboot.service.AdminService;
import itView.springboot.service.ProductService;
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
	private final ProductService pService;
	
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
		ReportDetail user = adService.selectReportDetail(userNo);
		model.addAttribute("user", user);
		model.addAttribute("page",page);
		
		return "admin/admin_report_detail";
		
	}
	
	//관리자 일반문의게시판 이동
	@GetMapping("/gBoard")
	public String gBoardPage(
			@RequestParam(value = "page", defaultValue = "1") int currentPage,
			HttpServletRequest request,
			Model model
			) {
	 	int gBoardListCount = adService.gBoardListCount(1);
        PageInfo pi = Pagination.getPageInfo(currentPage, gBoardListCount, 10);
        ArrayList<Board> gBoardList = adService.selectgBoardList(pi);

        model.addAttribute("gBoardList", gBoardList);
        model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());

		return"admin/admin_general_board";
	}
	
	//관리자 판매자 문의게시판 이동
	@GetMapping("/pBoard")
	public String pBoardPage(
		@RequestParam(value = "page", defaultValue = "1") int currentPage,
		HttpServletRequest request,
		Model model) {
	 	int pBoardListCount = adService.pBoardListCount(1);
        PageInfo pi = Pagination.getPageInfo(currentPage, pBoardListCount, 10);
        ArrayList<Board> pBoardList = adService.selectpBoardList(pi);

        model.addAttribute("pBoardList", pBoardList);
        model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());
        
		return"admin/admin_partner_board";
	}
		
	//관리자 판매금지게시판 (검색조회)
	@GetMapping("/proBoard")
	public String proBoardPage(
			@RequestParam(value = "page", defaultValue = "1") int currentPage,
	        @RequestParam(value = "value", required = false) String value,
	        @RequestParam(value = "condition", defaultValue = "all") String condition,
	        Model model,
	        HttpServletRequest request) {
		int prolistCount = adService.getproListCount(1, value, condition);

        PageInfo pi = Pagination.getPageInfo(currentPage, prolistCount, 10);

        ArrayList<prohibitProduct> prohibitList = adService.selecProhibitList(pi, value, condition);

        model.addAttribute("prohibitList", prohibitList);
        model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());
        model.addAttribute("value", value);
        model.addAttribute("condition", condition);

		
		return "admin/admin_prohibit_board";
	}
	
	//판매금지 글쓰기view
	@GetMapping("/proWrite")
	public String proEnrollPage(
			HttpSession session,
			Model model) {
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser == null) {
			model.addAttribute("msg", "로그인이 필요합니다.");
	        model.addAttribute("url", "/login/login");
	        return "common/sendRedirect";
		}
		 if (!"A".equals(loginUser.getUserType())) {
		        model.addAttribute("msg", "권한이 없습니다.");
		        model.addAttribute("url", "/");
		     return "common/sendRedirect";
		 }
	
		 return "admin/admin_prohibit_board_write";
	}
	
	
	//금지제품 등록하기
	@PostMapping("/proEnroll")
	public String proEnroll(
			HttpSession session, 
			@ModelAttribute Board b,
			@RequestParam(value="uploadedFiles", required=false) String uploadedFiles,
			Model model) {
		//작성자 확인 + 권한 확인
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser == null) {
			model.addAttribute("msg", "로그인이 필요합니다.");
	        model.addAttribute("url", "/login/login");
	        return "common/sendRedirect";
		}
		if (!"A".equals(loginUser.getUserType())) {
	        model.addAttribute("msg", "권한이 없습니다.");
	        model.addAttribute("url", "/");
	        return "common/sendRedirect";
		}
		
    	b.setUserNo(loginUser.getUserNo());
    	
    	if(uploadedFiles != null && !uploadedFiles.isEmpty()){
            String[] files = uploadedFiles.split(",");
            String content = b.getBoardContent();
            for(String fileName : files){
                // HTML 내 temp 경로를 notice 경로로 변경
                content = content.replace("/uploadFilesFinal/temp/" + fileName,
                        "/uploadFilesFinal/notice/" + fileName);
            }
            b.setBoardContent(content);
        }
    	
    	adService.proBoardEnroll(b, uploadedFiles, session);
    	
        model.addAttribute("msg", "공지를 등록하였습니다.");
        model.addAttribute("url", "/admin/proBoard");
        return "common/sendRedirect";
    }

	
	
	//판매금지 게시판 글 삭제
//	@PostMapping("/deleteProBoard")
//	public ResponseEntity<String> deleteProBoard(
//			@RequestBody Map<String, Object> request) {
//			int boardId = (int) request.get("id");
//
//	    // 서비스 호출해서 삭제 처리
//	    boolean deleted = adService.deleteBoard(boardId);
//
//	    if (deleted) {
//	        return ResponseEntity.ok("삭제 성공");
//	    } else {
//	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 실패");
//	    }
//	}
	
	
	
	
	
}
