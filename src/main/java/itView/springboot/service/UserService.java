package itView.springboot.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import itView.springboot.mapper.UserMapper;
import itView.springboot.vo.User;

@Service
public class UserService {
	private UserMapper userMapper;

	

}
