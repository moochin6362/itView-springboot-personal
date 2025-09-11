package itView.springboot.service;

import itView.springboot.mapper.InhoMapper;
import itView.springboot.mapper.InquiryMapper;
import itView.springboot.vo.Inquiry;
import lombok.RequiredArgsConstructor;
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
}
