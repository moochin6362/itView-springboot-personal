package itView.springboot.vo;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Getter;

	@Getter
	public class UserPrincipal implements UserDetails {

	    private final User user;

	    public UserPrincipal(User user) {
	        this.user = user;
	    }

	    // 계정의 권한 설정 (UserType 기반)
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        String role;
	        switch (user.getUserType()) {
	            case "a": role = "ROLE_ADMIN"; break;
	            case "p": role = "ROLE_PARTNER"; break;
	            case "u": role = "ROLE_USER"; break;
	            default: role = "ROLE_USER";
	        }
	        return Collections.singletonList(new SimpleGrantedAuthority(role));
	    }

	    @Override
	    public String getPassword() {
	        return user.getUserPassword();
	    }

	    @Override
	    public String getUsername() {
	        return user.getUserId();
	    }

	    // 계정 만료 여부
	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    // 계정 잠금 여부
	    @Override
	    public boolean isAccountNonLocked() {
	        return !"LOCKED".equalsIgnoreCase(user.getUserStatus());
	    }

	    // 비밀번호 만료 여부
	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    // 계정 활성화 여부
	    @Override
	    public boolean isEnabled() {
	        return "ACTIVE".equalsIgnoreCase(user.getUserStatus());
	    }
	
}
