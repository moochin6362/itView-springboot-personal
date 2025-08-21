package itView.springboot.product.model.vo;

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
public class Product {
	private int productNo;
	private String productName;
	private String productDetail;
	private int productStock;
	private int productPrice;
	private String productCategory;
	private String productCompany;
	private String productState;
	private int userNo;
	private String brandName;
	private String skinType;
	private String personalColor;
	private String skinTrouble;
	private String headSkin;
}
