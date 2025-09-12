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
}
