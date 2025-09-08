package itView.springboot.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class prohibitProduct {
	private int productNo;
	private String productName;
	private String productCategory;
	private String productCompany;
	private String productState;
	private String attmRename;
	private int boardId;
	private String boardTitle;
	private String boardContent;
	private Date boardDate;
	private Date boardModifiedDate;
	private String boardType;
	private String boardStatus;
    private String thumbnailPath;
}
