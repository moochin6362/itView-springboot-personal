package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class Cart {
	private int cartNo;
	private int amount;
	private int userNo;
	private int productNo;
	private String productCompany;
	private int productPrice;
	private String productName;
	private int productStock;
	private String productState;
}
