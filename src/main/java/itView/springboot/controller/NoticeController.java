package itView.springboot.controller;

import itView.springboot.common.Pagination;
import itView.springboot.service.NoticeService;
import itView.springboot.vo.Board;
import itView.springboot.vo.PageInfo;
import itView.springboot.vo.User;
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
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    //리스트 페이지
    @GetMapping("/list")
    public String list(@RequestParam(value= "page", defaultValue= "1") int currentPage,Model model, HttpServletRequest request) {
        int listCount = noticeService.getListCount(1);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10); // 현재페이지, 몇개가있는지, 몇개씩 보여질지

        List<Board> notices = noticeService.selectBoardList(pi);

        model.addAttribute("notices", notices);
        model.addAttribute("pi", pi);
        model.addAttribute("loc",request.getRequestURL());
        return "notice/list";
    }
    //작성폼 페이지
    @GetMapping("/write")
    public String writeForm() {
        return "notice/write";
    }

    //디테일 페이지
    @GetMapping("/detail")
    public String writeForm(@RequestParam("boardId") int boardId,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            Model model) {

        Board notice = noticeService.selectBoard(boardId);
        model.addAttribute("notice", notice);
        model.addAttribute("page", page);

        return "notice/detail";
    }

    // 작성
    @PostMapping("/insert")
    public String insertNotice(Board board,
                               @RequestParam(value="uploadedFiles", required=false) String uploadedFiles,
                               HttpSession session) {

        String[] files = uploadedFiles != null ? uploadedFiles.split(",") : new String[0];
        noticeService.insertNotice(board, files, session);

        // Query Parameter 방식으로 리다이렉트
        return "redirect:/notice/detail?boardId=" + board.getBoardId() + "&page=1";
    }


    // 보여지는 이미지
    @PostMapping("/uploadImage")
    @ResponseBody
    public String uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadDir = "c:/uploadFilesFinal/notice";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        File saveFile = new File(uploadDir, fileName);
        if (!saveFile.exists()) {
            saveFile.getParentFile().mkdirs();
        }
        file.transferTo(saveFile);

        // 반환값은 브라우저에서 사용할 URL
        return "/uploadFilesFinal/" + fileName;
    }

    // 삭제
    @GetMapping("/{boardId}/delete")
    public String deleteNotice(@PathVariable int boardId) {
        int notice = noticeService.deleteBoard(boardId);
        return "redirect:/notice/list";
    }

    // 업데이트 페이지
    @GetMapping("/{boardId}/updateForm")
    public String updateForm(@PathVariable int boardId, Model model) {
        Board notice = noticeService.selectBoard(boardId);
        model.addAttribute("notice", notice);
        return "notice/update";
    }
    // 업데이트
    @PostMapping("/update")
    public String update(Board board,
                         @RequestParam(value="uploadedFiles", required=false) String uploadedFiles,
                         HttpSession session) {

        // 1. 게시글 업데이트
        int result = noticeService.updateBoard(board);

        // 2. 업로드된 이미지가 있는 경우 DB에 저장
        String[] files = uploadedFiles != null ? uploadedFiles.split(",") : new String[0];
        if(files.length > 0) {
            User user = (User) session.getAttribute("loginUser");
            noticeService.insertAttachmentsForUpdate(board.getBoardId(), files);
        }

        return "redirect:/notice/" + board.getBoardId();
    }



}
