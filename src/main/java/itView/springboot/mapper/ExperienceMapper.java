package itView.springboot.mapper;

import itView.springboot.vo.Board;
import itView.springboot.vo.experienceGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ExperienceMapper {

    int getListCountWithSearch(@Param("keyword") String keyword,
                               @Param("type") String type);

    List<experienceGroup> selectBoardListWithSearch(@Param("rowBounds") RowBounds rowBounds,
                                                    @Param("keyword") String keyword,
                                                    @Param("type") String type);

    experienceGroup selectBoard(int boardId);
}
