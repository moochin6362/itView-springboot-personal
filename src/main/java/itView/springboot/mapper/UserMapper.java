package itView.springboot.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import itView.springboot.vo.User;

@Mapper
public interface UserMapper {
	//로그인
	User login(User u);
	 
	 //비밀번호 수정
	int updatePassword(User u);
	 
	 //아이디 찾기
	 User findId(
		 @Param("email")String email, 
		 @Param("userType") String userType);

	 
	 


	
}
