package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CouponBox {
	private int personalCouponNo;
	private String couponUsed;
	private int userNo;
	private int couponNo;
	
	
	private String couponName;
}
