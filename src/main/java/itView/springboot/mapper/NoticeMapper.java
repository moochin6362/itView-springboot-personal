package itView.springboot.mapper;

import itView.springboot.vo.Attachment;
import itView.springboot.vo.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    public void insertBoard(Board board);

    public void insertAttachment(Attachment attm);

    public List<Board> selectBoardList();

    Board selectBoard(int boardId);

    int updateBoard(Board board);
}
