package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Cart {
	private int cartNo;
	private int amout;
	private int userNo;
	private int productNo;
}
