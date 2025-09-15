package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ExperienceApplication {
	private int applyNo;
	private int expGroupNo;
	private int userNo;
	private String isChoice;
	private String applyContent;
	private String userName;
	private String expTitle;
	
	
}
