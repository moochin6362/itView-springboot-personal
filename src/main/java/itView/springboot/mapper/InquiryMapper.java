package itView.springboot.mapper;

import itView.springboot.vo.Inquiry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InquiryMapper {


    int insertinquiry(Inquiry inquiry);


    List<Inquiry> getInquirylist(int userNo);
}
