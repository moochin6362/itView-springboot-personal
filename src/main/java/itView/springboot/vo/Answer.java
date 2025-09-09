package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Answer {
	private int answerNo;
	private String answerContent;
	private String answerStatus;
	private int questionNo;
}
