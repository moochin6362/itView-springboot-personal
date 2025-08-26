package itView.springboot.mapper;
import org.apache.ibatis.annotations.Mapper;

import itView.springboot.vo.User;

@Mapper
public interface UserMapper {
	//회원조회
	 User findByUsername(String userId);

	 //kakao로그인
	 User selectUserByKakaoId(String kakaoId);
	 


	
}
