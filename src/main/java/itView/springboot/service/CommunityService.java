package itView.springboot.service;

import itView.springboot.mapper.CommunityMapper;
import itView.springboot.vo.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.io.File;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityMapper communityMapper;

    public void insertNotice(Board board, String uploadedFiles, HttpSession session) {
        User user = (User)session.getAttribute("loginUser");
        board.setUserNo(user.getUserNo());

        // 게시글 저장 → boardId 생성
        communityMapper.insertBoard(board);
        int boardId = board.getBoardId();

        if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
            String[] files = uploadedFiles.split(",");
            String tempDir = "c:/uploadFilesFinal/temp";
            String finalDir = "c:/uploadFilesFinal/community";

            File noticeFolder = new File(finalDir);
            if (!noticeFolder.exists()) {
                noticeFolder.mkdirs(); // ❗ 폴더 생성
            }

            for(String fileName : files){
                if(fileName == null || fileName.trim().isEmpty()) continue;

                File tempFile = new File(tempDir, fileName);
                File finalFile = new File(finalDir, fileName);

                // Windows에서 renameTo 실패할 경우 대비
                if (!tempFile.renameTo(finalFile)) {
                    java.nio.file.Path source = tempFile.toPath();
                    java.nio.file.Path target = finalFile.toPath();
                    try {
                        java.nio.file.Files.copy(source, target);
                        tempFile.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // DB 저장
                Attachment attm = new Attachment();
                attm.setAttmName(fileName.substring(fileName.indexOf("_")+1));
                attm.setAttmRename(fileName);
                attm.setAttmPath("/uploadFilesFinal/community");
                attm.setPositionNo(boardId);
                communityMapper.insertAttachment(attm);
            }
        }
    }

    public List<Board> selectBoardList(PageInfo pi) {
        int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return communityMapper.selectBoardList(rowBounds);
    }

    public Board selectBoard(int boardId) {
        return communityMapper.selectBoard(boardId);
    }

    public int updateBoard(Board board) {
        return communityMapper.updateBoard(board);
    }

    public void insertAttachmentsForUpdate(int boardId, String[] uploadedFiles) {
        if (uploadedFiles != null && uploadedFiles.length > 0) {
            String tempDir = "c:/uploadFilesFinal/temp";
            String finalDir = "c:/uploadFilesFinal/community";

            File noticeFolder = new File(finalDir);
            if (!noticeFolder.exists()) {
                noticeFolder.mkdirs();
            }

            for(String fileName : uploadedFiles){
                if(fileName == null || fileName.trim().isEmpty()) continue;

                File tempFile = new File(tempDir, fileName);
                File finalFile = new File(finalDir, fileName);

                // 이동 실패 시 copy + delete
                if(!tempFile.renameTo(finalFile)){
                    try {
                        java.nio.file.Files.copy(tempFile.toPath(), finalFile.toPath());
                        tempFile.delete();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                // DB 저장
                Attachment attm = new Attachment();
                attm.setAttmName(fileName.substring(fileName.indexOf("_")+1));
                attm.setAttmRename(fileName);
                attm.setAttmPath("/uploadFilesFinal/community");
                attm.setPositionNo(boardId);
                communityMapper.insertAttachment(attm);
            }
        }
    }


    public int getListCount(int i) {
        return communityMapper.getListCount(i);
    }

    public int getListCountWithSearch(int boardType, String keyword, String type) {
        return communityMapper.getListCountWithSearch(boardType, keyword, type);
    }

    public List<Board> selectBoardListWithSearch(PageInfo pi, String keyword, String type) {
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return communityMapper.selectBoardListWithSearch(rowBounds, keyword, type);
    }


    public int deleteBoard(int boardId) {
        return communityMapper.deleteBoard(boardId);
    }

    public Attachment selectFirstImage(int boardId, String boardType) {
        return communityMapper.selectFirstImage(boardId, boardType);
    }

    public int insertReply(Reply reply) {
        return communityMapper.insertReply(reply);
    }

    public int updateReply(Reply reply) {
        return communityMapper.updateReply(reply);
    }

    public int deleteReply(int replyNo) {
        return communityMapper.deleteReply(replyNo);
    }

    public List<Reply> selectReplyList(int boardId) {
        return communityMapper.selectReplyList(boardId);
    }

    public Reply findReplyById(Integer parentId) {
        return communityMapper.findReplyById(parentId);
    }
}