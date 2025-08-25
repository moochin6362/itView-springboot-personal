package itView.springboot.service;

import itView.springboot.vo.User;
import itView.springboot.vo.UserPrincipal;
import itView.springboot.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // DB에서 username(userId)로 사용자 조회
        User user = userMapper.findByUsername(userId); 
        if (user == null) {
            throw new UsernameNotFoundException(userId + " 사용자를 찾을 수 없습니다.");
        }

        // User를 UserPrincipal로 변환
        return new UserPrincipal(user);
    }
}
