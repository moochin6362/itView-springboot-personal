package itView.springboot.dto;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ReportDetail {
	private int reportNo;
	private String reportTitle;
	private Date reportDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date reportModifyDate;
	private String reportStatus;
	private String reportType;
	private int reporterUserId;
	 private int reportTargetNo;
	
	private int userNo;
    private String userId;
    private String userName;

}
