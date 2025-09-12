package itView.springboot.service;

import itView.springboot.mapper.InhoMapper;
import itView.springboot.mapper.InquiryMapper;
import itView.springboot.vo.Inquiry;
import itView.springboot.vo.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryMapper mapper;


    public int insertinquiry(Inquiry inquiry) {
        return mapper.insertinquiry(inquiry);
    }

    public List<Inquiry> getInquirylist(int userNo) {
        return mapper.getInquirylist(userNo);
    }

    public int getListCount(int userNo) {
        return mapper.getListCount(userNo);
    }

    public List<Inquiry> selectInquiryList(int userNo, PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return mapper.selectInquiryList(userNo, rowBounds);
    }

    public int getAllListCount() {
        return mapper.getAllListCount();
    }

    public List<Inquiry> selectAllInquiryList(PageInfo pi) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return mapper.selectAllInquiryList(rowBounds);
    }

    public int updateanswerContent(Inquiry inquiry) {
        return mapper.updateanswerContent(inquiry);
    }
}
