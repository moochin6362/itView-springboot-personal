package itView.springboot.mapper;
import org.apache.ibatis.annotations.Mapper;

import itView.springboot.vo.User;

@Mapper
public interface UserMapper {
	 //kakao로그인
	 User selectUserByKakaoId(String kakaoId);

	 //비밀번호 수정
	 int resetPwd(User u);
	 
	 //아이디 찾기
	 User findId(String email, String userPassword, String userType);
	 


	
}
