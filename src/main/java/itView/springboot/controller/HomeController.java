package itView.springboot.controller;

import itView.springboot.common.Pagination;
import itView.springboot.common.ProductMatchingRate;
import itView.springboot.service.InhoService;
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
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final InhoService uService;
    private final ProductService productService;

    @GetMapping
    public String home(@RequestParam(value="page", defaultValue="1") int currentPage,
                       Model model, HttpSession session,@RequestParam HashMap<String, String> map) {

        User loginUser = (User)session.getAttribute("loginUser");


        ArrayList<Product> list = uService.selectRankingList(map);

        if(loginUser != null) {
            ProductMatchingRate matcher = new ProductMatchingRate();
            for (Product p : list) {

                double rate = matcher.calculateMatchRate(loginUser, p);
                p.setMatchRate(rate); // Product VO에 추가해둔 matchRate 세팅
            }
        }

        // 매칭률 순으로 정렬 (선택사항)
        list.sort((p1, p2) -> {
            double m1 = p1.getMatchRate() != null ? p1.getMatchRate() : 0.0;
            double m2 = p2.getMatchRate() != null ? p2.getMatchRate() : 0.0;
            return Double.compare(m2, m1); // 내림차순 정렬
        });

        int listCount = uService.getRankingCount(map);
        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 16);

        int start = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        int end = Math.min(start + pi.getBoardLimit(), listCount);
        ArrayList<Product> plist = new ArrayList<>(list.subList(start, end));

        model.addAttribute("plist", plist);
        model.addAttribute("pi", pi);


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

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 12);

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

        PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 12);

        List<Product> products = productService.getFilterProducts(pi, q, categories);

        model.addAttribute("pi", pi);
        model.addAttribute("loc", request.getRequestURL());
        model.addAttribute("products", products);
//        model.addAttribute("selectedCategories", categories); // 선택된 필터 표시용
        model.addAttribute("q", q); // 검색어 유지용

        return "search/search";
    }



}
