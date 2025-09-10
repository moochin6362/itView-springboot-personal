package itView.springboot.vo;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminReply {
 	private int replyNo;             // REPLY_NO
    private String replyContent;      // REPLY_CONTENT
    private Date replyDate;           // REPLY_DATE
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date replyModifiedDate;   // REPLY_MODIFIED_DATE
    private String replyStatus;       // REPLY_STATUS
    private int boardId;             // BOARD_ID
    private int boardType;        // BOARD_TYPE
    private int userNo;              // USER_NO
}
