package itView.springboot.vo;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter

public class Wishlist {
	private int wishlistNo;
	private LocalDate wishDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private int productNo;
	private int userNo;
	
	private String productCompany;
	private int productPrice;
	private String productName;
	private int productStock;
	private String productState;
}
