package itView.springboot.mapper;

import itView.springboot.vo.Inquiry;
import itView.springboot.vo.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface InquiryMapper {


    int insertinquiry(Inquiry inquiry);


    List<Inquiry> getInquirylist(int userNo);

    int getListCount(int userNo);

    List<Inquiry> selectInquiryList(int userNo, RowBounds rowBounds);

    int getAllListCount();

    List<Inquiry> selectAllInquiryList(RowBounds rowBounds);

    int updateanswerContent(Inquiry inquiry);
}
