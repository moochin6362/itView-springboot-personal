package itView.springboot.service;

import java.io.File;
import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import itView.springboot.dto.GboardDetail;
import itView.springboot.dto.ReportDetail;
import itView.springboot.dto.UserReport;
import itView.springboot.mapper.AdminMapper;
import itView.springboot.mapper.InhoMapper;
import itView.springboot.vo.AdminReply;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Board;
import itView.springboot.vo.PageInfo;
import itView.springboot.vo.Product;
import itView.springboot.vo.Reply;
import itView.springboot.vo.Report;
import itView.springboot.vo.Review;
import itView.springboot.vo.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminMapper mapper;
	private final InhoMapper inhoMapper;

	//회원조회하기
	public int getUserListCount(int boardType, String value, String condition) {
		return mapper.getUserListCount(boardType, value, condition);
	}

	public ArrayList<User> selectUserList(PageInfo pi, String value, String condition) {
	   int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
	   RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
	   return mapper.selectUserList(rowBounds, value, condition);
	}

	//회원상세페이지
	public UserReport selectUser(int userNo) {
		return mapper.selectUser(userNo);
	}


	//일반 문의게시판조회 , 상세, 답변등록
		public Integer gBoardListCount(int boardType) {
			return mapper.gBoardListCount(boardType);
		}
		public ArrayList<GboardDetail> selectgBoardList(PageInfo pi) {
			int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
			RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
			return mapper.selectBoardList(rowBounds);
		}
		public GboardDetail gBoardDetail(int boardId) {
			return mapper.gBoardDetail(boardId);
		}
		//일반 문의게시판 답변등록
		public int saveGreply(AdminReply adminReply) {
			return mapper.saveGreply(adminReply);
		}
		

		//일반문의 - 댓글조회
		public ArrayList<AdminReply> getGeneralReplyList(int boardId) {
			return mapper.getGeneralReplyList(boardId);
		}


	
	//판매자 문의게시판
	public Integer pBoardListCount(int boardType) {
		return mapper.pBoardListCount(boardType);
	}
	public ArrayList<Board> selectpBoardList(PageInfo pi) {
		int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectpBoardList(rowBounds);
	}

	
	//판매금지 게시판(검색,조회)
	public Integer getproListCount(int boardType, String value, String condition) {
		return mapper.getproListCount(boardType, value, condition);
	}
	public ArrayList<Board> selecProhibitList(PageInfo pi, String value, String condition) {
		int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selecProhibitList(rowBounds, value, condition);
	}
	
	
	
	//판매금지 제품 등록
	public void proBoardEnroll(Board b, String uploadedFiles, HttpSession session) {
		 User user = (User)session.getAttribute("loginUser");
	        b.setUserNo(user.getUserNo());

	        // 게시글 저장 → boardId 생성
	        mapper.proBoardEnroll(b);
	        int boardId = b.getBoardId();

	        if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
	            String[] files = uploadedFiles.split(",");
	            String tempDir = "c:/uploadFilesFinal/temp";
	            String finalDir = "c:/uploadFilesFinal/notice";

	            File noticeFolder = new File(finalDir);
	            if (!noticeFolder.exists()) {
	                noticeFolder.mkdirs(); // ❗ 폴더 생성
	            }

	            for(String fileName : files){
	                if(fileName == null || fileName.trim().isEmpty()) continue;

	                File tempFile = new File(tempDir, fileName);
	                File finalFile = new File(finalDir, fileName);

	                // Windows에서 renameTo 실패할 경우 대비
	                if (!tempFile.renameTo(finalFile)) {
	                    java.nio.file.Path source = tempFile.toPath();
	                    java.nio.file.Path target = finalFile.toPath();
	                    try {
	                        java.nio.file.Files.copy(source, target);
	                        tempFile.delete();
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }

	                // DB 저장
	                Attachment attm = new Attachment();
	                attm.setAttmName(fileName.substring(fileName.indexOf("_")+1));
	                attm.setAttmRename(fileName);
	                attm.setAttmPath("/uploadFilesFinal/notice");
	                attm.setPositionNo(boardId);
	                inhoMapper.insertAttachment(attm);
	            }
	        }
	}

	public Board proBoardDetail(int boardId) {
		return mapper.proBoardDetail(boardId);
	}

	
	//판매금지 게시글 삭제
	public int deleteProBoard(int id) {
		return mapper.proBoardDelete(id);
	}

	
	//신고게시판 조회(페이징처리)
	public int getReportListCount1(int boardType, String value, String condition) {
		return mapper.getReportListCount1(boardType, value, condition);
	}
	public int getReportListCount2(int boardType, String value, String condition) {
		return mapper.getReportListCount2(boardType, value, condition);
	}
	public int getReportListCount3(int boardType, String value, String condition) {
		return mapper.getReportListCount3(boardType, value, condition);
	}
	
	public int getReportListCount4(int boardType, String value, String condition) {
		return mapper.getReportListCount4(boardType, value, condition);
	}

	//신고게시판 전체 리스트 조회
	public ArrayList<User> selectReportUserList(PageInfo pi, String value, String condition) {
		int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectReportUserList(rowBounds, value, condition);
	}

	public ArrayList<Board> selectReportBoardList(PageInfo pi, String value, String condition) {
		int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectReportBoardList(rowBounds, value, condition);
	}

	public ArrayList<Review> selectReportReviewList(PageInfo pi, String value, String condition) {
		int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectReportReviewList(rowBounds, value, condition);
	}

	public ArrayList<Reply> selectReportReplyList(PageInfo pi, String value, String condition) {
		int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectReportReplyList(rowBounds, value, condition);
	}
	
	
	//신고 상세페이지이동(해당신고 + 히스토리)
	//1.회원
	public User selectReportUser(int userNo) {
		User u = mapper.selectReportUser(userNo);
		return u;
	}
	public int getReportCount(int userNo) {
		return mapper.getReportCount(userNo);
	}

	public ArrayList<Report> getUserReportList(PageInfo pi, int userNo) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.getUserReportList(rowBounds, userNo);
	}

	//신고 커뮤니티글 상세보기
	public Board selectReportBoard(int boardId) {
		Board b = mapper.selectReportBoardList(boardId);
		return b;
	}

	public int getBoardReportCount(int boardId) {
		 return mapper.getBoardReportCount(boardId);
	}

	public ArrayList<Report> getBoardReportList(PageInfo pi, int boardId) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.getBoardReportList(rowBounds, boardId);
	}

	//신고 리뷰 상세보기
	public Review selectReportReview(int reviewNo) {
		Review rv = mapper.selectReportReview(reviewNo);
		return rv;
	}

	public int getReviewReportCount(int reviewNo) {
		return mapper.getReviewReportCount(reviewNo);
	}

	public ArrayList<Report> getReviewReportList(PageInfo pi, int reviewNo) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.getReviewReportList(rowBounds, reviewNo);
	}

	//신고 댓글 상세보기
	public Reply selectReportReply(int replyNo) {
		Reply v = mapper.selectReportReply(replyNo);
		return v;
	}

	public int getReplyReportCount(int replyNo) {
		return mapper.getReplyReportCount(replyNo);
	}

	public ArrayList<Report> getReplyReportList(PageInfo pi, Integer replyNo) {
		int offset = (pi.getCurrentPage()-1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.getReplyReportList(rowBounds, replyNo);
	}


	
	

	
	
	
	





		
		
		
		
		
		
		
}
	
	
	



