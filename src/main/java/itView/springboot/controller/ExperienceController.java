package itView.springboot.controller;

import itView.springboot.common.Pagination;
import itView.springboot.service.ExperienceService;
import itView.springboot.vo.Board;
import itView.springboot.vo.PageInfo;
import itView.springboot.vo.ExperienceGroup;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/experience")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    // 리스트 페이지
    @GetMapping("/list")
    public String list(
            @RequestParam(value = "page", defaultValue = "1") int currentPage,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "type", defaultValue = "all") String type,
            Model model,
            HttpServletRequest request) {

        // 검색 포함 게시글 수 가져오기
        int listCount = experienceService.getListCountWithSearch(keyword, type);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        List<ExperienceGroup> experiences = experienceService.selectBoardListWithSearch(pi, keyword, type);

        model.addAttribute("experiences", experiences);
        model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());
        model.addAttribute("keyword", keyword);
        model.addAttribute("type", type);

        return "experience/list";
    }

    // 디테일 페이지
    @GetMapping("/detail")
    public String writeForm(@RequestParam("expNo") int expNo,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            Model model) {

        ExperienceGroup experience = experienceService.selectBoard(expNo);
        model.addAttribute("experience", experience);
        model.addAttribute("page", page);

        return "experience/detail";
    }

}
