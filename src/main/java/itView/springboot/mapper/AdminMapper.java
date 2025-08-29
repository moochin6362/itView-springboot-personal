package itView.springboot.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import itView.springboot.vo.User;

@Mapper
public interface AdminMapper {
	
//회원조회
	public ArrayList<User> searchUserById(String value);

	public ArrayList<User> searchUserByType(String value);

	public ArrayList<User> searchUserByStatus(String value);

	public ArrayList<User> searchUserByReport(int count);
	
	public ArrayList<User> getAllUser();
}
