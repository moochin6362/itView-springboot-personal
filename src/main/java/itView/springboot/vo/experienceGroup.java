package itView.springboot.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class experienceGroup {
	private int expNo;
	private String expTitle;
	private String expContent;
	private Date expStart;
	private Date expEnd;
	private String expStatus;
	private int userNo;
}
