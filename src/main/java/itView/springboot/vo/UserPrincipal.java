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

    // UserType 반환 메서드 추가 (Override X)
    public String getUserType() {
        return user.getUserType();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String type = user.getUserType();
        String role;

        if (type == null) {
            role = "ROLE_USER"; // null이면(그럴리가 없겟지만) "U"디폴트 제공
        } else {
            switch (type) {
                case "A": role = "ROLE_ADMIN"; break;
                case "P": role = "ROLE_PARTNER"; break;
                case "U":
                default: role = "ROLE_USER"; break;
            }
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !"LOCKED".equalsIgnoreCase(user.getUserStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    
    //현재 활동중인 User상태값
    @Override
    public boolean isEnabled() {
        return "Y".equalsIgnoreCase(user.getUserStatus());
    }
}
