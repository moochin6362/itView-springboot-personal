package itView.springboot.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class Attachment {
	private int attmNo;
	private String attmName;
	private String attmRename;
	private String attmPath;
	private String attmStatus;
	private Date attmDate;
	private int attmLevel;
	private int attmPosition;
	private int positionNo;
}
