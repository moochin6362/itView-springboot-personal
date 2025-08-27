//package itView.springboot.service;
//
//import java.util.Map;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import itView.springboot.common.config.JwtTokenProvider;
//import itView.springboot.mapper.UserMapper;
//import itView.springboot.vo.User;
//import itView.springboot.vo.UserPrincipal;
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserMapper userMapper;
//	private final JwtTokenProvider jwtTokenProvider; // JWT 발급
//
//
//    @Override
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        // DB에서 username(userId)로 사용자 조회
//        User user = userMapper.findByUsername(userId); 
//        if (user == null) {
//            throw new UsernameNotFoundException(userId + " 사용자를 찾을 수 없습니다.");
//        }
//
//        // User를 UserPrincipal로 변환
//        return new UserPrincipal(user);
//    }
//   
//	
//	//kakao로그인
//	public String loginWithKakao(String accessToken) {
//        // 1. accessToken으로 카카오 REST API 호출 → 사용자 정보 조회(카카오 id, nickname 받아오기)
//        Map<String, Object> kakaoUser = getKakaoUserInfo(accessToken);
//        String kakaoId = kakaoUser.get("id").toString();
//        
//        Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoUser.get("kakao_account");
//        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
//        String nickname = profile != null ? (String) profile.get("nickname") : "카카오 회원";
//        
//        // 2. DB에서 해당 카카오 ID로 회원 조회/저장
//        User user = userMapper.selectUserByKakaoId(kakaoId);
//        if (user == null) {
//            throw new RuntimeException("가입되지 않은 카카오 계정입니다.");
//        }
//
//        return jwtTokenProvider.createToken(user.getUserId());
//    }
//	
//	//카카오 REST API 호출
//	private Map<String, Object> getKakaoUserInfo(String accessToken) {
//		RestTemplate restTemplate = new RestTemplate();
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.set("Authorization", "Bearer " + accessToken);
//	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//	    HttpEntity<String> entity = new HttpEntity<>("", headers);
//
//	    ResponseEntity<Map> response = restTemplate.exchange(
//	            "https://kapi.kakao.com/v2/user/me",
//	            HttpMethod.GET,
//	            entity,
//	            Map.class
//	    );
//
//	    return response.getBody();
//	}
//
//}
