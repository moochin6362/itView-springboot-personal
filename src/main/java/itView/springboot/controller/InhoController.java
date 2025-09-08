package itView.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import itView.springboot.common.Pagination;
import itView.springboot.common.ProductMatchingRate;
import itView.springboot.exception.AdminException;
import itView.springboot.service.InhoService;
import itView.springboot.vo.Board;
import itView.springboot.vo.Coupon;
import itView.springboot.vo.PageInfo;
import itView.springboot.vo.Point;
import itView.springboot.vo.Product;
import itView.springboot.vo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping({"/login", "/inhoAdmin"})
@RequiredArgsConstructor
@SessionAttributes("loginUser")
public class InhoController {
    private final InhoService uService;
    private final BCryptPasswordEncoder bcrypt;
    
    // 회원가입 페이지 이동
    @GetMapping("/signUp")
    public String signUpPage() {
        return "login/signUp";
    }
    
    @PostMapping("/signUp")
    public String insertSignUp(@ModelAttribute User u) {
    	String encPwd = bcrypt.encode(u.getUserPassword());
    	u.setUserPassword(encPwd);
    	
    	int result = uService.insertUser(u);
    	if(result > 0) {
    		
    		int signupPoint = uService.getPointByName("회원가입");
    		
    		Map<String, Object> map = new HashMap<>();
    		map.put("userNo", u.getUserNo());
    		map.put("point", signupPoint);
    		
    		int result2 = uService.addPoint(map);
    		if(result2 > 0) {
    			return "redirect:/login/login";
    		} else {
    			throw new AdminException("회원가입 포인트 지급을 실패하였습니다.");
    		}
    		
    	} else {
    		throw new AdminException("회원가입을 실패하였습니다.");
    	}
    }
    
    // 쿠폰 공지 등록 페이지 이동
    @GetMapping("/enrollCouponNotice")
    public String enrollCouponNotice() {
    	return "inhoAdmin/enrollCouponNotice";
    }
    
    @PostMapping("/enrollCouponNotice")
    public String enrollCouponNotice(HttpSession session, @ModelAttribute Board b,
    								@RequestParam(value="uploadedFiles", required=false) String uploadedFiles) {
    	User loginUser = (User)session.getAttribute("loginUser");
    	b.setUserNo(loginUser.getUserNo());
    	
    	if(uploadedFiles != null && !uploadedFiles.isEmpty()){
            String[] files = uploadedFiles.split(",");
            String content = b.getBoardContent();
            for(String fileName : files){
                // HTML 내 temp 경로를 notice 경로로 변경
                content = content.replace("/uploadFilesFinal/temp/" + fileName,
                        "/uploadFilesFinal/notice/" + fileName);
            }
            b.setBoardContent(content);
        }
    	
    	uService.enrollCouponNotice(b, uploadedFiles, session);
    	return "redirect:/inhoAdmin/couponBoardList";
    	
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
    
    // 쿠폰 등록 페이지 이동
    @GetMapping("/enrollCoupon")
    public String enrollCouponPage() {
        return "inhoAdmin/enrollCoupon";
    }
    
    @PostMapping("/enrollCoupon")
    public String enrollCoupon(HttpSession session, @ModelAttribute Coupon c) {
    	User loginUser = (User)session.getAttribute("loginUser");
    	c.setUserNo(loginUser.getUserNo());
    	
    	int result1 = uService.enrollCoupon(c);
    	
    	if (result1 > 0) {
    		List<User> targetUser = uService.selectTargetUser(c.getCouponTarget());
    		
    		for(User u : targetUser) {
    			Map<String, Object> map = new HashMap<>();
    			map.put("userNo", u.getUserNo());
    			map.put("couponNo", c.getCouponNo());
    			
    			// 회원에게 쿠폰 자동 발급
    			int result2 = uService.insertCouponBox(map);
    			if(result2 > 0) {
    			} else {
    	    		throw new AdminException("회원에게 쿠폰발급을 실패하였습니다.");
    			}
    		}
    		return "redirect:/inhoAdmin/couponList";
    	} else {
    		throw new AdminException("쿠폰 등록을 실패하였습니다.");
    	}
    }
    
    // 신고누적상품 관리 페이지 이동
    @GetMapping("/productDetail")
    public String productDetailPage() {
        return "inhoAdmin/productDetail";
    }
    
    // 쿠폰리스트 페이지
    @GetMapping({"/couponList", "couponSearch"})
    public String couponList(@RequestParam(value="page", defaultValue="1") int currentPage,
    							Model model, HttpServletRequest request, @RequestParam HashMap<String, String> map) {

    	int listCount = uService.getCouponCount(map);
    	PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
    	ArrayList<Coupon> list = uService.selectCouponList(pi,map);
    	
    	model.addAttribute("list", list);
    	model.addAttribute("pi", pi);
    	model.addAttribute("loc", request.getRequestURI());
    	model.addAttribute("map", map);
    	
        return "inhoAdmin/couponList";
    }
    
    // 쿠폰 상세페이지
    @GetMapping("/coupon/{id}/{page}")
    public String selectCoupon(@PathVariable("id") int cId, @PathVariable("page") int page, Model model) {
    	Coupon c = uService.selectCoupon(cId);
    	if(c != null) {
    		model.addAttribute("c", c);
    		model.addAttribute("page", page);
    		return "inhoAdmin/couponDetail";
    	} else {
    		throw new AdminException("쿠폰 상세보기를 실패하였습니다.");
    	}
    }
    
    // 쿠폰 수정 페이지 이동
    @GetMapping("updateCoupon")
    public String updateCouponPage(@RequestParam("couponNo") int cId, @RequestParam("page") int page, Model model) {
    	Coupon c = uService.selectCoupon(cId);
    	model.addAttribute("c", c);
        model.addAttribute("page", page);
    	return "inhoAdmin/updateCoupon";
    }
    
    // 쿠폰 수정
    @PostMapping("/updateCoupon")
    public String updateCoupon(@ModelAttribute Coupon c) {
    	int result = uService.updateCoupon(c);
    	if(result > 0) {
    		return "redirect:/inhoAdmin/couponList";
    	} else {
    		throw new AdminException("쿠폰 수정을 실패하였습니다.");
    	}
    }
    
    // 쿠폰 공지사항 리스트 페이지 이동
    @GetMapping({"/couponBoardList", "couponBoardSearch"})
    public String couponBoardList(@RequestParam(value="page", defaultValue="1") int currentPage,
			Model model, HttpServletRequest request, @RequestParam HashMap<String, String> map) {
    	
    	int listCount = uService.getCouponBoardCount(map);
    	PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
    	ArrayList<Board> list = uService.selectCouponBoardList(pi,map);

    	model.addAttribute("list", list);
    	model.addAttribute("pi", pi);
    	model.addAttribute("loc", request.getRequestURI());
    	model.addAttribute("map", map);
    	
        return "inhoAdmin/couponBoardList";
    }
    
    // 쿠폰 공지사항 상세 페이지
    @GetMapping("/couponBoard/{id}/{page}")
    public String selectCouponBoard(@PathVariable("id") int bId, @PathVariable("page") int page, Model model) {
    	Board b = uService.selectCouponBoard(bId);
    	if(b != null) {
    		model.addAttribute("b", b);
    		model.addAttribute("page", page);
    		return "inhoAdmin/updateCouponNotice";
    	} else {
    		throw new AdminException("쿠폰 공지 상세보기를 실패하였습니다.");
    	}
    }
    
    // 쿠폰 공지사항 수정
    @PostMapping("updateCouponBoard")
    public String updateCouponBoard(Board b, @RequestParam(value="uploadedFiles", required=false) String uploadedFiles,
           							HttpSession session) {
    	
    	// 에디터 HTML 내 temp → notice 경로 변경
        if(uploadedFiles != null && !uploadedFiles.isEmpty()){
            String[] files = uploadedFiles.split(",");
            String content = b.getBoardContent();
            for(String fileName : files){
                content = content.replace("/uploadFilesFinal/temp/" + fileName,
                        "/uploadFilesFinal/notice/" + fileName);
            }
            b.setBoardContent(content);
        }

        // 게시글 업데이트
        uService.updateCouponBoard(b);

        // 이미지 temp → notice 이동 및 DB 저장
        uService.updateAttachment(b.getBoardId(), uploadedFiles != null ? uploadedFiles.split(",") : new String[0]);

        return "redirect:/inhoAdmin/couponBoardList";
    }
    
    // 포인트 리스트 페이지
    @GetMapping({"/pointList", "/pointSearch"})
    public String pointList(@RequestParam(value="page", defaultValue="1") int currentPage,
							Model model, HttpServletRequest request, @RequestParam HashMap<String, String> map) {
    	
    	int listCount = uService.getPointCount(map);
    	PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
    	ArrayList<Point> list = uService.selectPointList(pi,map);
    	
    	model.addAttribute("list", list);
    	model.addAttribute("pi", pi);
    	model.addAttribute("loc", request.getRequestURI());
    	model.addAttribute("map", map);
    	
        return "inhoAdmin/pointList";
    }
    
    // 포인트 등록 페이지 이동
    @GetMapping("/enrollPoint")
    public String enrollPointPage() {
        return "inhoAdmin/enrollPoint";
    }
    
    @PostMapping("/enrollPoint")
    public String enrollPoint(HttpSession session, @ModelAttribute Point p) {
    	
    	User loginUser = (User)session.getAttribute("loginUser");
    	p.setUserNo(loginUser.getUserNo());
    	
    	int result = uService.enrollPoint(p);
    	
    	if(result > 0) {
    		return "redirect:/inhoAdmin/pointList";
    	} else {
    		throw new AdminException("포인트 등록을 실패하였습니다.");
    	}
    }
    
    // 포인트 수정 페이지 이동
    @GetMapping("/updatePoint")
    public String updatePointPage(@ModelAttribute Point p) {
        return "inhoAdmin/updatePoint";
    }
    
    @PostMapping("/updatePoint")
    public String updatePoint(@ModelAttribute Point p) {
    	int result = uService.updatePoint(p);
    	
    	if(result > 0) {
    		return "redirect:/inhoAdmin/updatePoint";
    	} else {
    		throw new AdminException("포인트 수정을 실패하였습니다.");
    	}
    }
    
    @GetMapping("/ranking")
    public String rankingList(@RequestParam(value="page", defaultValue="1") int currentPage, 
			Model model, HttpServletRequest request, @RequestParam HashMap<String, String> map,
			HttpSession session) {
    	
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
    	
    	model.addAttribute("list", plist);
    	model.addAttribute("pi", pi);
    	model.addAttribute("loc", request.getRequestURI());
    	model.addAttribute("map", map);
    	
    	return "inhoAdmin/ranking";
    }
    
}
