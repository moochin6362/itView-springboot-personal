package itView.springboot.service;


import org.springframework.stereotype.Service;

import itView.springboot.mapper.UserMapper;
import itView.springboot.vo.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserMapper userMapper;
	
	//비밀번호 수정
	public int resetPwd(User u) {
		return userMapper.resetPwd(u);
	}

	//아이디 찾기
	public User findId(String email, String userType) {
		return userMapper.findId(email,userType);
	}

	

}
