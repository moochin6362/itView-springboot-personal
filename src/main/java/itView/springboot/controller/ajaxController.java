package itView.springboot.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import itView.springboot.exception.AdminException;
import itView.springboot.service.AdminService;
import itView.springboot.service.UserService;
import itView.springboot.vo.AdminReply;
import itView.springboot.vo.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/login", "/admin"})
@SessionAttributes("loginUser")
public class ajaxController {
	private final UserService uService;
	private final AdminService adService;
	//이메일 샌더 설정
	private final JavaMailSender mailSender;
	
	
	// 이메일 인증
    @GetMapping("/emailCheck")
    public String checkEmail(@RequestParam("email") String email) {
       MimeMessage mimeMessage = mailSender.createMimeMessage();
       
      // 수신자, 제목, 본문 설정
      String subject = "[it:View] 이메일 인증 안내";
      String body = "<div style='font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 30px;'>";

      body += "<div style='max-width: 600px; margin: auto; background: #ffffff; border-radius: 12px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); overflow: hidden;'>";

      // 헤더 영역 (브랜드 로고 느낌)
      body += "<div style='background: linear-gradient(135deg, #34e5eb, #1bb6bb); padding: 20px; text-align: center;'>";
      body += "<h1 style='color: #fff; margin: 0; font-size: 28px;'>이쁘지? it:View지!</h1>";
      body += "<p style='color: #fff; margin: 5px 0 0; font-size: 14px;'>당신의 아름다움을 위한 뷰티 파트너</p>";
      body += "</div>";

      // 본문 영역
      body += "<div style='padding: 30px; text-align: center; color: #333;'>";
      body += "<h2 style='margin-bottom: 20px; color: #34e5eb;'>이메일 인증 요청</h2>";
      body += "<p style='font-size: 16px; line-height: 1.6; margin-bottom: 30px;'>"
            + "안녕하세요, it:View를 이용해주셔서 감사합니다.<br>"
            + "아래 인증번호를 입력하시면 회원가입 및 서비스 이용이 완료됩니다.<br>"
            + "본 메일은 본인 확인을 위해 발송되었습니다."
            + "</p>";
      
      // 인증번호 5자리 만들기
      String random = "";
      for(int i = 0 ; i < 5; i++) {
         random += (int)(Math.random() * 10); // 0 <= n < 1
      }
      System.out.println(random);
      
      // 인증번호 박스
      body += "<div style='background: #e9fcfd; border: 2px dashed #34e5eb; border-radius: 8px; display: inline-block; padding: 20px 40px; margin-bottom: 30px;'>";
      body += "<span style='font-size: 32px; font-weight: bold; color: #129ca1; letter-spacing: 5px;'>" + random + "</span>";
      body += "</div>";

      body += "<p style='font-size: 14px; color: #777;'>※ 인증번호는 보안을 위해 5분간만 유효합니다.</p>";

      body += "</div>";

      // 푸터 영역
      body += "<div style='background: #fafafa; padding: 15px; text-align: center; font-size: 12px; color: #888;'>"
            + "본 메일은 발신 전용으로 회신이 불가능합니다.<br>"
            + "ⓒ it:View All Rights Reserved."
            + "</div>";

      body += "</div></div>";
      
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
      try {
         mimeMessageHelper.setTo(email);
         mimeMessageHelper.setSubject(subject);
         mimeMessageHelper.setText(body, true);;
      } catch (MessagingException e) {
         e.printStackTrace();
      }
      
      mailSender.send(mimeMessage); // Email 전송부분
      
      return random;
    }
    
	//판매금지 게시판 글 삭제
	@DeleteMapping("/deleteProBoard/{boardId}")
	@ResponseBody
	public ResponseEntity<String>deleteProBoard(
			@PathVariable("boardId") int boardId){
		int result = adService.deleteProBoard(boardId);
		
		if(result > 0 ) {
			return ResponseEntity.ok("공지사항을 삭제하였습니다.");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("공지사항 삭제에 실패하였습니다.");
		}
	}
	 //관리자 일반게시판 문의댓글 달기
		@PostMapping("gReply/{boardId}")
		@ResponseBody
		public ResponseEntity<AdminReply> addReply(
				@PathVariable("boardId") int boardId,
				@RequestBody AdminReply adminReply,
				HttpSession session){
			
			User loginUser = (User) session.getAttribute("loginUser");
			if(adminReply.getReplyContent() == null || adminReply.getReplyContent().trim().isEmpty()) {
				throw new AdminException("댓글 내용을 입력하세요.");
			}
			
			//권한체크
			if(loginUser != null && "A".equals(loginUser.getUserType())) {
				adminReply.setUserNo(loginUser.getUserNo());
				adminReply.setBoardType(3);
				adminReply.setBoardId(boardId);
				
				int result = adService.saveGreply(adminReply);
				if(result > 0) {
					return ResponseEntity.ok(adminReply);
				} else {
					throw new AdminException("댓글 등록에 실패하였습니다.");
				} 
			} else {
				 return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                     .body(null);
			}
			
		}
		
		//일반게시판 문의댓글 리스트 뽑아오기
		@GetMapping("gReplyList")
		@ResponseBody
		public ArrayList<AdminReply> getReplyList(
				@RequestParam("boardId") int boardId){
			ArrayList<AdminReply> replyList = adService.getGeneralReplyList(boardId);
			return replyList;
		}
		
		
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
