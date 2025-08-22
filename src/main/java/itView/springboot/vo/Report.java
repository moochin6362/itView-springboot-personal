package itView.springboot.vo;

import java.sql.Date;

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
	private Date reportModifyDate;
	private String reportStatus;
	private String reportType;
	private int reporterUserId;
	private int reportTargetNo;
}
