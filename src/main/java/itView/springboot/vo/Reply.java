package itView.springboot.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Reply {
	private int replyNo;
	private String replyContent;
	private Date replyDate;
	private Date replyModifiedDate;
	private int parentId;
	private String replyStatus;
	private int boardId;
	private int userNo;
}
