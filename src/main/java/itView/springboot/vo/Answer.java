package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Answer {
	private int answerNo;
	private String answerContent;
	private String answerStatus;
	private int questionNo;
	
	private String productName;
	private String productCompany;
}
