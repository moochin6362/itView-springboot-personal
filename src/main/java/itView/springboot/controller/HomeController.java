package itView.springboot.controller;

import itView.springboot.common.Pagination;
import itView.springboot.service.InquiryService;
import itView.springboot.service.ProductService;
import itView.springboot.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {


    private final ProductService productService;

    @GetMapping
    public String home(Model model) {
        List<Product> products = productService.getLatestProducts();
        model.addAttribute("products", products);
        return "index"; // templates/views/index.html
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "page", defaultValue = "1") int currentPage,
                         @RequestParam(value = "q", required = false) String q,
                         HttpServletRequest request,
                         Model model) {

        if (q == null || q.isBlank()) {
            q = "";
        }

        // 검색 포함 게시글 수 가져오기
        int listCount = productService.getListCountWithSearch(q);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        List<Product> products = productService.getSearchProducts(pi, q);
        model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());

        model.addAttribute("products", products);

        return "search/search";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(value = "page", defaultValue = "1") int currentPage,
                         @RequestParam(value = "q", required = false) String q,
                         @RequestParam(value = "category", required = false) List<String> categories,
                         HttpServletRequest request,
                         Model model) {

        if (q == null || q.isBlank()) q = "";
        if (categories == null) categories = new ArrayList<>();

        // 검색 포함 게시글 수 가져오기
        int listCount = productService.getListCountWithFilter(q, categories);

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

        List<Product> products = productService.getFilterProducts(pi, q, categories);

        model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());
        model.addAttribute("products", products);
//        model.addAttribute("selectedCategories", categories); // 선택된 필터 표시용
        model.addAttribute("q", q); // 검색어 유지용

        return "search/search";
    }



}
