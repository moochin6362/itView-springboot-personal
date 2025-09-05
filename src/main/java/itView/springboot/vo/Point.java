package itView.springboot.vo;

import java.sql.Date;

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
	private Date pointCreatedate;
	private Date pointEnddate;
	private int userNo;
	
	private String userName;
}
