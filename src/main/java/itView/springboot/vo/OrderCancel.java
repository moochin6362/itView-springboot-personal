package itView.springboot.vo;

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
public class OrderCancel {
	private int cancelNo;
	private int userNo;
	private int orderNo;
	private String cancelReason;
	private String cancelDetail;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate cancelDate;
	private int orderTargetNo;
}
