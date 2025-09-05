package itView.springboot.controller;

import itView.springboot.common.Pagination;
import itView.springboot.service.CommunityService;
import itView.springboot.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    // 리스트 페이지
    @GetMapping("/list")
    public String list(
            @RequestParam(value = "page", defaultValue = "1") int currentPage,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "type", defaultValue = "all") String type,
            Model model,
            HttpServletRequest request) {

        int listCount = communityService.getListCountWithSearch(1, keyword, type);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 8);

        List<Board> communitys = communityService.selectBoardListWithSearch(pi, keyword, type);

        // 썸네일 이미지 셋팅
        for (Board board : communitys) {
            Attachment thumbnail = communityService.selectFirstImage(board.getBoardId(), "1"); // boardType = "1"
            if (thumbnail != null) {
                board.setThumbnailPath("/uploadFilesFinal/community/" + thumbnail.getAttmRename());
            }
        }

        model.addAttribute("communitys", communitys);
        model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);

        return "community/list";
    }



    // 작성폼 페이지
    @GetMapping("/write")
    public String writeForm() {
        return "community/write";
    }

    // 댓글 목록 불러오기 (디테일에서 같이)
    @GetMapping("/detail")
    public String detail(@RequestParam("boardId") int boardId,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         Model model) {

        Board community = communityService.selectBoard(boardId);
        List<Reply> replies = communityService.selectReplyList(boardId);

        model.addAttribute("community", community);
        model.addAttribute("replies", replies);
        model.addAttribute("page", page);

        return "community/detail";
    }

    // 작성
    @PostMapping("/insert")
    public String insertNotice(Board board,
                               @RequestParam(value="uploadedFiles", required=false) String uploadedFiles,
                               HttpSession session) {

        if(uploadedFiles != null && !uploadedFiles.isEmpty()){
            String[] files = uploadedFiles.split(",");
            String content = board.getBoardContent();
            for(String fileName : files){
                // HTML 내 temp 경로를 notice 경로로 변경
                content = content.replace("/uploadFilesFinal/temp/" + fileName,
                        "/uploadFilesFinal/community/" + fileName);
            }
            board.setBoardContent(content);
        }

        communityService.insertNotice(board, uploadedFiles, session);
        return "redirect:/community/detail?boardId=" + board.getBoardId() + "&page=1";
    }


    // 업로드 (임시 폴더)
    @PostMapping("/uploadImage")
    @ResponseBody
    public String uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String tempDir = "c:/uploadFilesFinal/temp";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        File saveFile = new File(tempDir, fileName);
        if (!saveFile.exists()) {
            saveFile.getParentFile().mkdirs();
        }
        file.transferTo(saveFile);

        return "/uploadFilesFinal/temp/" + fileName;
    }

    // 삭제
    @GetMapping("/{boardId}/delete")
    public String deleteNotice(@PathVariable int boardId) {
        int notice = communityService.deleteBoard(boardId);
        return "redirect:/community/list";
    }

    // 업데이트 페이지
    @GetMapping("/{boardId}/updateForm")
    public String updateForm(@PathVariable int boardId, Model model) {
        Board community = communityService.selectBoard(boardId);
        model.addAttribute("community", community);
        return "community/update";
    }

    // 업데이트
    @PostMapping("/update")
    public String update(Board board,
                         @RequestParam(value="uploadedFiles", required=false) String uploadedFiles,
                         HttpSession session) {

        // 에디터 HTML 내 temp → notice 경로 변경
        if(uploadedFiles != null && !uploadedFiles.isEmpty()){
            String[] files = uploadedFiles.split(",");
            String content = board.getBoardContent();
            for(String fileName : files){
                content = content.replace("/uploadFilesFinal/temp/" + fileName,
                        "/uploadFilesFinal/community/" + fileName);
            }
            board.setBoardContent(content);
        }

        // 게시글 업데이트
        communityService.updateBoard(board);

        // 이미지 temp → notice 이동 및 DB 저장
        communityService.insertAttachmentsForUpdate(board.getBoardId(), uploadedFiles != null ? uploadedFiles.split(",") : new String[0]);

        return "redirect:/community/detail?boardId=" + board.getBoardId() + "&page=1";
    }

    // 댓글 등록
    @PostMapping("/reply/insert")
    public String insertReply(Reply reply, HttpSession session) {
        // 로그인 사용자 세팅
        reply.setUserNo(((User) session.getAttribute("loginUser")).getUserNo());

        // 댓글/대댓글 구분
        if (reply.getParentId() != null && reply.getParentId() == 0) {
            // 일반 댓글
            reply.setParentId(null);
        } else if (reply.getParentId() != null) {
            // 대댓글이면 최상위 댓글 ID 가져오기
            Reply parent = communityService.findReplyById(reply.getParentId()); // 부모 댓글 조회
            if (parent.getParentId() == null) {
                // 부모가 최상위 댓글이면 그대로
                reply.setParentId(parent.getReplyNo());
            } else {
                // 부모가 대댓글이면 최상위 댓글 ID로 교체
                reply.setParentId(parent.getParentId());
            }
        }

        communityService.insertReply(reply);
        return "redirect:/community/detail?boardId=" + reply.getBoardId();
    }


    // 댓글 수정
    @PostMapping("/reply/edit")
    public String editReply(Reply reply, HttpSession session) {
        communityService.updateReply(reply);
        return "redirect:/community/detail?boardId=" + reply.getBoardId();
    }

    // 댓글 삭제
    @PostMapping("/reply/delete")
    public String deleteReply(@RequestParam("replyNo") int replyNo,
                              @RequestParam("boardId") int boardId) {
        communityService.deleteReply(replyNo);
        return "redirect:/community/detail?boardId=" + boardId;
    }


}