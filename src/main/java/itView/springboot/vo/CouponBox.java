package itView.springboot.vo;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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
	private String couponMinprice;
	private int couponDiscount;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate couponStartdate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate couponEnddate;
	private String couponTarget;
	private String brandName;
	
}
