package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Category {
	private int categoryNo;
	private String categoryName;
	private int categoryParent;
	private String categoryDescription;
}
