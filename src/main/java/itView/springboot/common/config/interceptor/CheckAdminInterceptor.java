package itView.springboot.common.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import itView.springboot.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CheckAdminInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false); // 세션 없으면 null 반환
        if (session == null || session.getAttribute("loginUser") == null) {
            // 로그인 안 된 경우
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>alert('관리자만 접근 가능합니다.'); location.href='/';</script>");
            response.getWriter().flush();
            return false;
        }

        // 세션에서 로그인 유저 정보 가져오기
        User loginUser = (User)session.getAttribute("loginUser");

        if (!"A".equals(loginUser.getUserType())) {
            // 일반 유저일 경우
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("<script>alert('관리자만 접근 가능합니다.'); location.href='/';</script>");
            response.getWriter().flush();
            return false;
        }

        return true; // 관리자이면 정상 진행
    
	}
}
