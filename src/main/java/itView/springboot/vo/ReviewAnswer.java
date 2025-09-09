package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewAnswer {

	private int reviewAnswerId;
	private String reviewAnswerContent;
	private String reviewAnswerStatus;
	private String userNo;
	private String reviewNo;
}
