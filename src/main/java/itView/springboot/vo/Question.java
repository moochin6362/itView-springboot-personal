package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Question {
	private int questionNo;
	private String questionTitle;
	private String questionContent;
	private String questionStatus;
	private int userNo;
	private int productNo;
	
	private String userName;
	private String productName;
}
