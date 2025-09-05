package itView.springboot.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Reply {
	private Integer replyNo;
	private String replyContent;
	private Date replyDate;
	private Date replyModifiedDate;
	private Integer parentId;
	private String replyStatus;
	private Integer boardId;
	private Integer userNo;

	// 댓글 작성자 이름 추가
	private String userName;
}
