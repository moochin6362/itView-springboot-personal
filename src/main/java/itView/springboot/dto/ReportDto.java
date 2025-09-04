package itView.springboot.dto;


import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ReportDto {
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
	
	
	private String targetUserId;   // 신고받은 사용자 ID
    private int reportCount;       // 해당 사용자가 받은 신고 총 수
}
