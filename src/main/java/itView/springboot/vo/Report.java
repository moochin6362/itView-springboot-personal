package itView.springboot.vo;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Report {
	private int reportNo;
	private String reportTitle;
	private String reportContent;
	private Date reportDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date reportModifyDate;
	private String reportStatus;
	private String reportType;
	private int reporterUserId;
	private int reportTargetNo;
	private String reportReplyStatus;
	
	private int productNo;
	private String productName;
	private String userName;
}
