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
public class Review {
	private int reviewNo;
	private String reviewContent;
	private int reviewRate;
	private Date reviewDate;
	private Date reviewModifyDate;
	private int productNo;
	private int userNo;
	private String userName;
	
    //신고용(DB에는 없음)
	private String reportType;
	private Date reportDate;
	private String reportTitle;
	private int reportCount;
	private int rowNum;
	private String reportStatus;
}
