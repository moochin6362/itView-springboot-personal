package itView.springboot.service;

import java.io.File;
import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import itView.springboot.dto.ReportDetail;
import itView.springboot.dto.UserReport;
import itView.springboot.dto.prohibitProduct;
import itView.springboot.mapper.AdminMapper;
import itView.springboot.mapper.InhoMapper;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Board;
import itView.springboot.vo.PageInfo;
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

	
	//신고당한 회원 리스트
	public int getReportListCount(int boardType, String value, String condition) {
		return mapper.getReportListCount(boardType, value, condition);
	}
	
	public ArrayList<User> selecReportList(PageInfo pi, String value, String condition) {
		int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectReportList(rowBounds, value, condition);
	}
	
	//신고게시판 상세보기
	public ReportDetail selectReportDetail(int userNo) {
		return mapper.selectReportDetail(userNo);
	}
	//일반 문의게시판
	public Integer gBoardListCount(int boardType) {
		return mapper.gBoardListCount(boardType);
	}
	public ArrayList<Board> selectgBoardList(PageInfo pi) {
		int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.selectBoardList(rowBounds);
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
	public ArrayList<prohibitProduct> selecProhibitList(PageInfo pi, String value, String condition) {
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
		
		
		
		
		
		
		
		
}
	
	
	



