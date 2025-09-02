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

	//회원상세조회
	UserReport selectUser(int userNo);

	
	


}
