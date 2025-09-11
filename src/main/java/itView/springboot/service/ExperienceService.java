package itView.springboot.service;

import itView.springboot.mapper.AdminMapper;
import itView.springboot.mapper.ExperienceMapper;
import itView.springboot.vo.Board;
import itView.springboot.vo.PageInfo;
import itView.springboot.vo.ExperienceGroup;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceMapper mapper;

    public int getListCountWithSearch(String keyword, String type) {
        return mapper.getListCountWithSearch(keyword, type);
    }

    public List<ExperienceGroup> selectBoardListWithSearch(PageInfo pi, String keyword, String type) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return mapper.selectBoardListWithSearch(rowBounds, keyword, type);
    }

    public ExperienceGroup selectBoard(int boardId) {
        return mapper.selectBoard(boardId);
    }
}
