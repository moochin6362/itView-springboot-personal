package itView.springboot.controller;

import itView.springboot.common.Pagination;
import itView.springboot.service.CommunityService;
import itView.springboot.vo.Board;
import itView.springboot.vo.PageInfo;
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

        // 검색 포함 게시글 수 가져오기
        int listCount = communityService.getListCountWithSearch(1, keyword, type);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 8);

        List<Board> communitys = communityService.selectBoardListWithSearch(pi, keyword, type);

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

    // 디테일 페이지
    @GetMapping("/detail")
    public String writeForm(@RequestParam("boardId") int boardId,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            Model model) {

        Board community = communityService.selectBoard(boardId);
        model.addAttribute("community", community);
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

}