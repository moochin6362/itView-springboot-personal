package itView.springboot.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import itView.springboot.mapper.AdminMapper;
import itView.springboot.vo.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final AdminMapper mapper;
	
//회원조회하기
	//1-1 : 아이디로 회원조회
	public ArrayList<User> searchUserById(String value) {
		return mapper.searchUserById(value);
	}
	//1-2 : 타입으로 조회
	public ArrayList<User> searchUserByType(String value) {
		return mapper.searchUserByType(value);
	}
	//1-3 : 상태로 조회
	public ArrayList<User> searchUserByStatus(String value) {
		return mapper.searchUserByStatus(value);
	}
	//1-4 : 타입으로 조회
	public ArrayList<User> searchUserByReport(int count) {
		return mapper.searchUserByReport(count);
	}
	//1-5 : 전체회원 조회
	public ArrayList<User> getAllUser() {
		return mapper.getAllUser();
	}

}
