package itView.springboot.mapper;

import itView.springboot.vo.Attachment;
import itView.springboot.vo.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface NoticeMapper {
    public void insertBoard(Board board);

    public void insertAttachment(Attachment attm);

    public List<Board> selectBoardList(RowBounds rowBounds);

    Board selectBoard(int boardId);

    int updateBoard(Board board);

    int getListCount(int i);

    int deleteBoard(int boardId);
}
