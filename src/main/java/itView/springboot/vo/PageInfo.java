package itView.springboot.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PageInfo {
    private int currentPage; //현재 페이지
    private int listCount;   //총 게시글
    private int pageLimit;   //페이지 하단에 페이징바
    private int maxPage;     //총페이지

    private int startPage;   //시작페이지
    private int endPage;	 //끝페이지
    private int boardLimit;  //한페이지에 보여질 갯수
}
