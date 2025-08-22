package itView.springboot.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Board {
	private int boardId;
	private String boardTitle;
	private String boardContent;
	private Date boardDate;
	private Date boardModifiedDate;
	private String boardType;
	private String boardStatus;
	private int userNo;
}
