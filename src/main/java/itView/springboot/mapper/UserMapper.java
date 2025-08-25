package itView.springboot.mapper;
import org.apache.ibatis.annotations.Mapper;

import itView.springboot.vo.User;

@Mapper
public interface UserMapper {
	//회원조회
	 User findByUsername(String userId);

	 //kakao로그인
	 User selectUserByKakaoId(String kakaoId);
	 
	 //회원 등록(회원가입 할 떄 수정할것)
	 void insertUser(User user);

	
}
