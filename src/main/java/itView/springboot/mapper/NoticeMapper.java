package itView.springboot.mapper;

import itView.springboot.vo.Attachment;
import itView.springboot.vo.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    List<Board> selectBoardListWithSearch(@Param("rowBounds") RowBounds rowBounds,
                                          @Param("keyword") String keyword,
                                          @Param("type") String type);

    int getListCountWithSearch(@Param("boardType") int boardType,
                               @Param("keyword") String keyword,
                               @Param("type") String type);


    int deleteBoard(int boardId);
}
