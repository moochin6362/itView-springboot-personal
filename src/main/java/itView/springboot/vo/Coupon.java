package itView.springboot.vo;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Coupon {
	private int couponNo;
	private String couponName;
	private String couponDescription;
	private String couponTarget;
	private int couponDiscount;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate couponStartdate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate couponEnddate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate couponCreatedate;
	private int couponMinprice;
	private int userNo;
	private String couponStatus;
	
	// join시 가져올 이름
	private String userName;
	private String brandName;
	private String userGrade;
}
