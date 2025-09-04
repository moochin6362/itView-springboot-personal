package itView.springboot.vo;

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
	private String skinType;
	private String personalColor;
	private String skinTrouble;
	private String headSkin;
	
	// join시 가져올 이름
	private String brandName;
	private String userAge;
	private String gender;

	// 그냥 Attachment.attmRename 필드만 추가
	private String attmRename;


}
