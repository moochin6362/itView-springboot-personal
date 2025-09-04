package itView.springboot.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import itView.springboot.dto.UserReport;
import itView.springboot.vo.User;

@Mapper
public interface AdminMapper {

	//회원조회
	int getUserListCount(
			@Param("boardType") int boardType, 
			@Param("value") String value, 
			@Param("condition")String condition);
	
	ArrayList<User> selectUserList(
			@Param("rowBounds") RowBounds rowBounds,
			@Param("value") String value, 
			@Param("condition")String condition);

	//회원상세보기
	UserReport selectUser(int userNo);

	
	//신고게시판 조회
	int getReportListCount(int boardType, String value, String condition);

	ArrayList<User> selectReportList(RowBounds rowBounds, String value, String condition);

	//신고게시판 상세보기
	UserReport selectReport(int userNo);

	
	


}
