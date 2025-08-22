package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ExperienceApplication {
	private int applyNo;
	private int expGroupNo;
	private int userNo;
	private String isChoice;
	private String applyContent;
}
