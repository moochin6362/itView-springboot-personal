package itView.springboot.vo;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Order {
	private int orderNo;
	private int orderTargetNo;
	private LocalDate orderDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private int orderCount;
	private String orderStatus;
	private String paymentMethod;
	private int payPrice;
	private String deliveryCompany;
	private int deliveryNo;
	private String deliveryStatus;
	private int orderType;
	private int userNo;
	private int personalCouponNo;
	
	private int productNo;
	private String productName;
	private int productPrice;
	private int productStock;
	private String productState;
	private String productCompany;
	
	private String userPhone;
	private String userAddress;
	private String userName;
	
	private String attmRename;
}
