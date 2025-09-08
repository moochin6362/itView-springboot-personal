package itView.springboot.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import itView.springboot.dto.ReportDetail;
import itView.springboot.dto.UserReport;
import itView.springboot.dto.prohibitProduct;
import itView.springboot.vo.Board;
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
	int getReportListCount(
			@Param("boardType") int boardType, 
			@Param("value") String value, 
			@Param("condition")String condition);

	ArrayList<User> selectReportList(
			@Param("rowBounds") RowBounds rowBounds,
			@Param("value") String value, 
			@Param("condition")String condition);

	//신고게시판 상세보기
	ReportDetail selectReportDetail(int userNo);
	
	
	//일반 문의게시판 조회
	Integer gBoardListCount(
			@Param("boardType") int boardType);
	ArrayList<Board> selectBoardList(
			@Param("rowBounds") RowBounds rowBounds);
	
	//판매자 문의게시판 조회
	Integer pBoardListCount
		(@Param("boardType") int boardType);

	ArrayList<Board> selectpBoardList(
		@Param("rowBounds") RowBounds rowBounds);

	
	//판매금지 게시판 조회
	Integer getproListCount(
			@Param("boardType") int boardType, 
			@Param("value") String value, 
			@Param("condition")String condition);

	ArrayList<prohibitProduct> selecProhibitList(
			@Param("rowBounds") RowBounds rowBounds,
			@Param("value") String value, 
			@Param("condition")String condition);

	//판매금지제품 등록
	int proBoardEnroll(Board b);


		
		


	}
