package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class User {
	private int userNo;
	private String username;
	private String userGender;
	private int userAge;
	private String userPhone;
	private String userType; //p:판매자 a:관리자 u:일반소비자
	private String userId;
	private String userPassword;
	private String userStatus;
	private int userPoint;
	private String userAddress;
	private String userGrade;
	private String email;
	private String brandName;
	private String skinType;
	private String personalColor;
	private String skinTrouble;
	private String headSkin;
}
