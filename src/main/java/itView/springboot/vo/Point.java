package itView.springboot.vo;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Point {
	private int pointNo;
	private String pointName;
	private String pointDescription;
	private int pointValue;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate pointCreatedate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate pointEnddate;
	private int userNo;
	
	private String userName;
}
