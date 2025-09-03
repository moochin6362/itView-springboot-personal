package itView.springboot.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Wishlist {
	private int wishlistNo;
	private Date wishDate;
	private int productNo;
	private int userNo;
	
	private String productCompany;
	private int productPrice;
	private String productName;
}
