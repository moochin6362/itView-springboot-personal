package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Question {
	private int questionNo;
	private String questionTitle;
	private String questionContent;
	private String questionStatus;
	private int userNo;
	private int productNo;
}
