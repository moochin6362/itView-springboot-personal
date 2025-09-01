package itView.springboot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserReport {
	private int userNo;
    private String userId;
    private String email;
    private String brandName;
    private String userName;
    private String userType;
    private String userStatus;
    private int reportCount;
}
