package itView.springboot.service;

import org.springframework.stereotype.Service;

import itView.springboot.mapper.ProductMapper;
import itView.springboot.vo.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductMapper mapper;

	public User login(User u) {
		return mapper.login(u);
	}

}
