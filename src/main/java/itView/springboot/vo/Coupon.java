package itView.springboot.vo;

import java.sql.Date;

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
	private Date couponStartdate;
	private Date couponEnddate;
	private Date couponCreatedate;
	private int couponMinprice;
	private int userNo;
	private String couponStatus;
	
	// join시 가져올 이름
	private String userName;
	private String brandName;
}
