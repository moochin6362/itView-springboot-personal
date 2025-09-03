package itView.springboot.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Order {
	private int orderNo;
	private int orderTargetNo;
	private Date orderDate;
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
	
	private String productName;
	private int productPrice;
	
	private String userPhone;
	private String userAddress;
	private String userName;
}
