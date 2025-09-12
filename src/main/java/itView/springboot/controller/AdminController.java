package itView.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import itView.springboot.service.InquiryService;
import itView.springboot.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import itView.springboot.common.Pagination;
import itView.springboot.dto.GboardDetail;
import itView.springboot.dto.ReportDetail;
import itView.springboot.dto.UserReport;
import itView.springboot.exception.AdminException;
import itView.springboot.service.AdminService;
import itView.springboot.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminController {
	private final AdminService adService;
	private final ProductService pService;
	private final InquiryService inquiryService;

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
	
	//관리자 일반문의게시판 이동
	@GetMapping("/gBoard")
	public String gBoardPage(
			@RequestParam(value = "page", defaultValue = "1") int currentPage,
			HttpServletRequest request,
			Model model
			) {
		int listCount = inquiryService.getAllListCount();
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5);
		List<Inquiry> inquiryList = inquiryService.selectAllInquiryList(pi);

        model.addAttribute("inquiryList", inquiryList);
        model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());

		return"admin/admin_general_board";
	}

	//관리자 일반문의게시판 답변작성
	@PostMapping("/inquiry/answer")
	public String inquiryAnswer(@ModelAttribute Inquiry inquiry) {
		int result = inquiryService.updateanswerContent(inquiry);

		return "redirect:/admin/gBoard";
	}
	
	//일반문의 상세
	@GetMapping("gBoardDetail")
	public String gBoardDetailPage(
			@RequestParam("boardId") int boardId,
			@RequestParam(value="page", defaultValue="1") int page,
			Model model) {
		GboardDetail gBoard = adService.gBoardDetail(boardId);
		model.addAttribute("gBoard", gBoard);
		model.addAttribute("page",page);
		
		return "admin/admin_general_board_detail";
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

        ArrayList<Board> prohibitList = adService.selecProhibitList(pi, value, condition);

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

	//판매금지 게시판 상세페이지
	@GetMapping("proDetail")
	public String proDetailPage(
			@RequestParam("boardId")int boardId,
			@RequestParam(value="page", defaultValue="1") int page,
			Model model, HttpSession session) {
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
		
			Board proBoard = adService.proBoardDetail(boardId);
			model.addAttribute("proBoard", proBoard);
			model.addAttribute("page",page);
			System.out.println(proBoard);
			
		return "admin/admin_prohibit_detail";
	}
	
	
	//신고게시판 회원용(회원U, 게시판B, 댓글V, 리뷰R)
	@GetMapping("/rUser")
	public String rUserList(
		@RequestParam(value = "page", defaultValue = "1") int currentPage,
        @RequestParam(value = "value", required = false) String value,
        @RequestParam(value = "condition", defaultValue = "all") String condition,
        Model model,
        HttpServletRequest request
        ) {
		int listCount = adService.getReportListCount1(1, value, condition);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        ArrayList<User> userList = adService.selectReportUserList(pi, value, condition);
 
        model.addAttribute("userList", userList);
        model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());
        model.addAttribute("value", value);
        model.addAttribute("condition", condition);
        
        //System.out.println(userList);
        return "admin/admin_rUser_board";
	}
	
	//신고 게시판(커뮤니티 글)
	@GetMapping("/rBoard")
	public String rBoardList(
			@RequestParam(value = "page", defaultValue = "1") int currentPage,
	        @RequestParam(value = "value", required = false) String value,
	        @RequestParam(value = "condition", defaultValue = "all") String condition,
	        Model model,
	        HttpServletRequest request
	        ) {
		int listCount = adService.getReportListCount2(1, value, condition);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

    
		 ArrayList<Board> boardList = adService.selectReportBoardList(pi, value, condition);
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());
        model.addAttribute("value", value);
        model.addAttribute("condition", condition);
		return "admin/admin_rBoard_board";
	}
	
	//신고게시판 (리뷰)
	@GetMapping("/rReview")
	public String rReviewList(
			@RequestParam(value = "page", defaultValue = "1") int currentPage,
	        @RequestParam(value = "value", required = false) String value,
	        @RequestParam(value = "condition", defaultValue = "all") String condition,
	        Model model,
	        HttpServletRequest request
	        ) {
		int listCount = adService.getReportListCount3(1, value, condition);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

		ArrayList<Review> reviewList = adService.selectReportReviewList(pi, value, condition);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());
        model.addAttribute("value", value);
        model.addAttribute("condition", condition);
		
		return "admin/admin_rReview_board";
	}
	
	//신고게시판 (댓글)
	@GetMapping("/rReply")
	public String rReplyList(
			@RequestParam(value = "page", defaultValue = "1") int currentPage,
	        @RequestParam(value = "value", required = false) String value,
	        @RequestParam(value = "condition", defaultValue = "all") String condition,
	        Model model,
	        HttpServletRequest request
	        ) {
		int listCount = adService.getReportListCount4(1, value, condition);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        
		ArrayList<Reply> replyList = adService.selectReportReplyList(pi, value, condition);
		model.addAttribute("replyList", replyList);
		model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());
        model.addAttribute("value", value);
        model.addAttribute("condition", condition);
		
        
		return "admin/admin_rReply_board";
	}
	
	
	
	//신고 게시판 상세조회
	@GetMapping("/rUserDetail")
	public String rUserDetail(
			@RequestParam("userNo")int userNo,
			@RequestParam(value="page", defaultValue="1") int page,
			Model model
			) {
		//신고 당한 회원 정보 상세
		User u = adService.selectReportUser(userNo);
		int listCount = adService.getReportCount(userNo);
		PageInfo pi = Pagination.getPageInfo(page, listCount, 5);
		ArrayList<Report> rllist = adService.getUserReportList(pi, userNo);
		
		if(u != null) {
			model.addAttribute("u", u);
			model.addAttribute("rllist", rllist);
			model.addAttribute("page", page);
			return "admin/admin_rUser_detail";
		} else {
			throw new AdminException("신고 회원 상세보기를 실패하였습니다.");
		}
	}
	@GetMapping("/rBoardDetail")
	public String rBoardDetail(
			@RequestParam("boardId")int boardId,
			@RequestParam(value="page", defaultValue="1") int page,
			Model model) {
			//신고 당한 회원 정보 상세
			Board b = adService.selectReportBoard(boardId);
			int blistCount = adService.getBoardReportCount(boardId);
			PageInfo pi = Pagination.getPageInfo(page, blistCount, 5);
			ArrayList<Report> blist = adService.getBoardReportList(pi, boardId);
			
			if(b != null) {
				model.addAttribute("b", b);
				model.addAttribute("blist", blist);
				model.addAttribute("page", page);
				return "admin/admin_rBoard_detail";
			} else {
				throw new AdminException("신고글 상세보기를 실패하였습니다.");
			}
	}
	
	
	@GetMapping("/rReviewDetail")
	public String rReviewDetail(
			@RequestParam("reviewNo")int reviewNo,
			@RequestParam(value="page", defaultValue="1") int page,
			Model model
			) {
		Review rv = adService.selectReportReview(reviewNo);
		int rlistCount = adService.getReviewReportCount(reviewNo);
		PageInfo pi = Pagination.getPageInfo(page, rlistCount, 5);
		ArrayList<Report> rlist = adService.getReviewReportList(pi, reviewNo);
		System.out.println(rv);
		if(rv != null) {
			model.addAttribute("rv", rv);
			model.addAttribute("rlist", rlist);
			model.addAttribute("page", page);
			return "admin/admin_rReview_detail";
		} else {
			throw new AdminException("신고글 상세보기를 실패하였습니다.");
		}
		
	}
	@GetMapping("/rReplyDetail")
	public String rReplyDetail(
			@RequestParam("replyNo")Integer replyNo,
			@RequestParam(value="page", defaultValue="1") int page,
			Model model
			) {
		Reply v = adService.selectReportReply(replyNo);
		int vlistCount = adService.getReplyReportCount(replyNo);
		PageInfo pi = Pagination.getPageInfo(page, vlistCount, 5);
		ArrayList<Report> vlist = adService.getReplyReportList(pi, replyNo);
		
		if(v != null) {
			model.addAttribute("v", v);
			model.addAttribute("vlist", vlist);
			model.addAttribute("page", page);
			return "admin/admin_rReply_detail";
		} else {
			throw new AdminException("신고글 상세보기를 실패하였습니다.");
		}
	}
	
	
	
	
	
	
	
}
