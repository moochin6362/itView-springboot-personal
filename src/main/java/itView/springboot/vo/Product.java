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
	private String userAge;
	private String gender;
	private String ingredient;
	private String ecoFriendly;
	
	// join시 가져올 이름
	private String brandName;

	// 그냥 Attachment.attmRename 필드만 추가
	private String attmRename;
	
	// DB 저장 X, 화면에만 쓰는 값
	private transient Double matchRate;  


}
