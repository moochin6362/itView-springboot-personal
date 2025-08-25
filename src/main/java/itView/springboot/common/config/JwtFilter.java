package itView.springboot.common.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import itView.springboot.service.CustomUserDetailsService;
import itView.springboot.vo.UserPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String jwt = null;

        // 1. Authorization 헤더에서 JWT 추출
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        } 
        // 2. 없으면 쿠키에서 JWT 추출
        else if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("JWT_TOKEN".equals(c.getName())) {
                    jwt = c.getValue();
                    break;
                }
            }
        }

        try {
            if (jwt != null) {
                // 토큰에서 username 추출
                String username = jwtUtil.getUsernameFromToken(jwt);

                // SecurityContext에 인증정보가 없으면
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserPrincipal userDetails = (UserPrincipal) userDetailsService.loadUserByUsername(username);

                    // 토큰 유효성 검사
                    if (jwtUtil.validateToken(jwt)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, 
                                        null, 
                                        userDetails.getAuthorities()
                                );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException | IllegalArgumentException e) {
            e.printStackTrace();
            SecurityContextHolder.clearContext();
            clearJwtCookie(response);
        } catch (Exception e) {
            e.printStackTrace();
            SecurityContextHolder.clearContext();
        }
    }

    private void clearJwtCookie(HttpServletResponse response) {
        Cookie expiredCookie = new Cookie("JWT_TOKEN", null);
        expiredCookie.setPath("/");
        expiredCookie.setMaxAge(0);
        expiredCookie.setHttpOnly(true);
        response.addCookie(expiredCookie);
    }
}
