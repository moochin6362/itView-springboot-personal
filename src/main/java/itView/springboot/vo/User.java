package itView.springboot.vo;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class User {
	private int userNo; //pk
	private String userName; //실명
	private String userGender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate userAge;
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
	private String kakaoId;
	private String ingredient;
	private String ecoFriendly;
	private String hopePrice;
	
    //신고용(DB에는 없음)
	private int reportNo;
	private String reportTitle;
	private String reportType;
	private int reportCount;
	private int rowNum;
	private String reportStatus;
	private Date reportDate;
	private Date reportModifyDate;
	private int reportTargetNo;
}
