package itView.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;

import itView.springboot.vo.User;

@Mapper
public interface ProductMapper {

	User login(User u);

	

}

