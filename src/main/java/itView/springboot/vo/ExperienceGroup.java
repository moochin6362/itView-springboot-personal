package itView.springboot.vo;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ExperienceGroup {
	private int expNo;
	private String expTitle;
	private String expContent;
	private Date expStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expEnd;
	private String expStatus;
	private int userNo;

	// 닉네임
	private String userName;
	
	// 체험단 신청 대기 개수
	private int eaCount;
}
