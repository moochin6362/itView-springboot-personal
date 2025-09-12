package itView.springboot.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Inquiry {

    private int questionNo;           //시퀀스
    private String questionTitle;     //제목
    private String questionContent;   //문의
    private String answerContent;     //답변
    private int userNo;               //유저

    private String userName;

}