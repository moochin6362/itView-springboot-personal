package itView.springboot.common.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class adminLoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//로그인 유저 불러오기
		//만약 로그인 유저가 null이고 관리자가 아니라면 접근권한 막기 ->home으로 보내기
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
