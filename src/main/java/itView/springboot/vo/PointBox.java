package itView.springboot.vo;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter @Setter
public class PointBox {
    private int pointNo;
    private int userNo;
    private String pointName;
    private String pointDescription;
    private int pointValue; // +적립 / -차감
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pointCreatedate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pointEnddate;
}
