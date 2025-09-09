package itView.springboot.common.config.interceptor;

import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FlashMessageInterceptor implements HandlerInterceptor {
	@Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        if (modelAndView == null || modelAndView.getViewName() == null) return;

        String viewName = modelAndView.getViewName();
        String uri = request.getRequestURI();

        // redirect + update/delete 경로일 때만 메시지 생성
        if (viewName.startsWith("redirect:")) {
            String msg = null;

            if (uri.contains("/enroll")) {
                msg = "등록 완료되었습니다";
            } else if (uri.contains("/update")) {
                msg = "수정 완료되었습니다";
            } else if (uri.contains("/delete")) {
                msg = "삭제되었습니다";
            }

            if (msg != null) {
                FlashMap flashMap = new FlashMap();
                flashMap.put("msg", msg);

                FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);
                if (flashMapManager != null) {
                    flashMapManager.saveOutputFlashMap(flashMap, request, response);
                }
            }
        }
    }
}
