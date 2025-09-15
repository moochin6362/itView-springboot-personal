package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewAnswer {

	private int reviewAnswerId;
	private String reviewAnswerContent;
	private String reviewAnswerStatus;
	private String userNo;
	private int reviewNo;
	
	// 답변 개수 가져오기
	private int productNo;
	private int reviewAnswerCount;
}
