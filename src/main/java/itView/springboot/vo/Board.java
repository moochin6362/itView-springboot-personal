package itView.springboot.vo;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate boardDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate boardModifiedDate;
	private String boardType;
	private String boardStatus;
	private int userNo;
	private String boardReplyStatus;

    // 👉 썸네일 경로 (DB에는 없음)
    private String thumbnailPath;
    private String userName;
    private String brandName;
    
    //관리자 금지제품 검색
    private String productName;
    
    //신고용(DB에는 없음)
    private int reportNo;
	private String reportType;
	private Date reportDate;
	private String reportTitle;
	private int reportCount;
	private int rowNum;
	private String reportStatus;
	private Date reportModifyDate;
	private int reportTargetNo;
}