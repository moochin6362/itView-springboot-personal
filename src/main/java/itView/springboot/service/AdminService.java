package itView.springboot.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import itView.springboot.dto.UserReport;
import itView.springboot.mapper.AdminMapper;
import itView.springboot.vo.PageInfo;
import itView.springboot.vo.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminMapper mapper;

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
	public UserReport selectReport(int userNo) {
		return mapper.selectReport(userNo);
	}
	
	
	
	


}
