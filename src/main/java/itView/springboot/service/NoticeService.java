package itView.springboot.service;

import itView.springboot.mapper.NoticeMapper;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Board;
import itView.springboot.vo.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeMapper noticeMapper;

    @Transactional
    public void insertNotice(Board board, String[] uploadedFiles, HttpSession session) {
        User user = (User)session.getAttribute("loginUser");
        board.setUserNo(user.getUserNo());

        // 게시글 저장 → boardId 생성
        noticeMapper.insertBoard(board);
        int boardId = board.getBoardId();

        // 업로드된 이미지가 있는 경우에만 DB 저장
        if (uploadedFiles != null && uploadedFiles.length > 0) {
            for(String fileName : uploadedFiles) {
                // 파일명 유효성 체크
                if (fileName == null || fileName.trim().isEmpty()) continue;

                Attachment attm = new Attachment();
                attm.setAttmName(fileName.substring(fileName.indexOf("_") + 1)); // 원본명
                attm.setAttmRename(fileName); // UUID+원본명
                attm.setAttmPath("c:/uploadFilesFinal/notice");
                attm.setPositionNo(boardId);

                noticeMapper.insertAttachment(attm);
            }
        }
    }
    //리스트
    public List<Board> selectBoardList() {
        return noticeMapper.selectBoardList();
    }

    //디테일화면
    public Board selectBoard(int boardId) {
        return noticeMapper.selectBoard(boardId);
    }

    //업데이트보드
    public int updateBoard(Board board) {
        return noticeMapper.updateBoard(board);
    }
    //업데이트 이미지
    @Transactional
    public void insertAttachmentsForUpdate(int boardId, String[] uploadedFiles) {
        if (uploadedFiles != null && uploadedFiles.length > 0) {
            for(String fileName : uploadedFiles) {
                if (fileName == null || fileName.trim().isEmpty()) continue;

                Attachment attm = new Attachment();
                attm.setAttmName(fileName.substring(fileName.indexOf("_") + 1)); // 원본명
                attm.setAttmRename(fileName); // UUID+원본명
                attm.setAttmPath("c:/uploadFilesFinal/notice"); // 업로드 경로
                attm.setPositionNo(boardId);

                noticeMapper.insertAttachment(attm);
            }
        }
    }
}
