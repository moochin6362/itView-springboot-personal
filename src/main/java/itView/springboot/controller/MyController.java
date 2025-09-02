package itView.springboot.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import itView.springboot.service.MyService;
import itView.springboot.vo.Product;
import itView.springboot.vo.Review;
import itView.springboot.vo.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController {

	private final MyService myService;
	// MyController.java
	private static final String LOGIN_URL = "/";

	/** 마이페이지(프로필/연령대/등급 표시) */
	@GetMapping("/myPage")
	public String myPage(HttpSession session, Model model) {
		Long userNo = getUserNo(session);
		if (userNo == null)
			return "redirect:/"; // ⬅ 폴백 제거, 로그인으로

		User u = myService.getUser(userNo);
		if (u != null) {
			model.addAttribute("profileName", u.getUserName());
			model.addAttribute("profileAge", toAgeRange(u.getUserAge()));
			model.addAttribute("profileGrade", toGradeName(u.getUserGrade()));
		} else {
			model.addAttribute("profileName", "로그인 사용자");
			model.addAttribute("profileAge", "연령대 미상");
			model.addAttribute("profileGrade", "일반");
		}

		String url = myService.getProfileImageUrl(userNo);
		model.addAttribute("profileImageUrl", withCacheBuster(url));
		return "my/myPage";
	}

	/** 프로필 이미지 업로드(교체) */
	@PostMapping("/profile-image")
	public String uploadProfile(@RequestParam("file") MultipartFile file, HttpSession session, RedirectAttributes ra) {
		Long userNo = getUserNo(session);
		if (userNo == null) {
			ra.addFlashAttribute("msg", "세션이 만료되었습니다. 다시 로그인해주세요.");
			return "redirect:/";
		}
		try {
			myService.updateProfileImage(userNo, file); // 디스크 저장 + ATTACHMENT 반영
			ra.addFlashAttribute("msg", "프로필이 변경되었습니다.");
		} catch (Exception e) {
			log.error("프로필 변경 실패", e);
			ra.addFlashAttribute("msg", "프로필 변경 실패: " + e.getMessage());
		}
		return "redirect:/my/myInfo";
	}

	@GetMapping("/myInfo")
	public String myInfo(HttpSession session, Model model) {
	    Long userNo = getUserNo(session);
	    if (userNo == null) return "redirect:/";

	    // 1) 프로필 이미지
	    String url = myService.getProfileImageUrl(userNo);
	    model.addAttribute("profileImageUrl", withCacheBuster(url));

	    // 2) 사용자 정보 로드
	    User u = myService.getUser(userNo);
	    model.addAttribute("user", u);

	    // 3) 체크박스(피부 고민)용 리스트
	    model.addAttribute("concernList", splitCsv(u != null ? u.getSkinTrouble() : null));

	    return "my/myInfo";
	}

	private java.util.List<String> splitCsv(String s) {
	    if (s == null || s.isBlank()) return java.util.Collections.emptyList();
	    return java.util.Arrays.stream(s.split("\\s*,\\s*"))
	            .filter(t -> !t.isBlank())
	            .toList();
	}


	/** 내 정보 저장 */
	@PostMapping("/info")
	public String saveInfo(@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "nickname", required = false) String userName,
			@RequestParam(name = "gender", required = false) String userGender,
			@RequestParam(name = "ageRange", required = false) Integer ageRange,
			@RequestParam(name = "skinType", required = false) String skinType,
			@RequestParam(name = "personalColor", required = false) String personalColor,
			@RequestParam(name = "concerns", required = false) String[] concerns,
			@RequestParam(name = "headSkin", required = false) String headSkin, HttpSession session,
			RedirectAttributes ra) {
		Long userNo = getUserNo(session);
		if (userNo == null) {
			ra.addFlashAttribute("msg", "세션이 만료되었습니다. 다시 로그인해주세요.");
			return "redirect:/login";
		}

		User u = new User();
		u.setUserNo(Math.toIntExact(userNo));
		u.setEmail(emptyToNull(email));
		u.setUserName(emptyToNull(userName));
		u.setUserGender(emptyToNull(userGender));
		if (ageRange != null)
			u.setUserAge(ageRange); // 10/20/30…
		u.setSkinType(emptyToNull(skinType));
		u.setPersonalColor(emptyToNull(personalColor));
		u.setSkinTrouble((concerns != null && concerns.length > 0) ? String.join(",", concerns) : null);
		u.setHeadSkin(emptyToNull(headSkin));

		int rows = myService.updateUserBasicAndSkin(u);
		ra.addFlashAttribute("msg", rows > 0 ? "내 정보가 저장되었습니다." : "저장할 변경 사항이 없습니다.");
		return "redirect:/my/myPage";
	}

	// ================== userNo 획득(폴백 없음) ==================
	private Long getUserNo(HttpSession session) {
		// 1) 세션에 userNo가 이미 있으면
		Object o = session.getAttribute("userNo");
		if (o instanceof Number)
			return ((Number) o).longValue();

		// 2) 세션에 로그인 사용자 객체가 있으면 (프로젝트마다 키 이름 다를 수 있음)
		try {
			Object loginUser = session.getAttribute("loginUser");
			if (loginUser instanceof User lu && lu.getUserNo() > 0) {
				Long no = (long) lu.getUserNo();
				session.setAttribute("userNo", no);
				return no;
			}
		} catch (Throwable ignore) {
		}

		// 3) 세션에 로그인 ID만 저장되어 있으면 (예: "userId" 또는 "loginId")
		for (String key : new String[] { "userId", "loginId" }) {
			Object idObj = session.getAttribute(key);
			if (idObj instanceof String s && !s.isBlank()) {
				Long no = myService.findUserNoByUserId(s);
				if (no != null) {
					session.setAttribute("userNo", no);
					return no;
				}
			}
		}

		// 4) Spring Security 사용 시 Authentication.getName()
		try {
			Class<?> sch = Class.forName("org.springframework.security.core.context.SecurityContextHolder");
			Object ctx = sch.getMethod("getContext").invoke(null);
			if (ctx != null) {
				Object auth = ctx.getClass().getMethod("getAuthentication").invoke(ctx);
				if (auth != null) {
					String userId = String.valueOf(auth.getClass().getMethod("getName").invoke(auth));
					if (userId != null && !userId.isBlank()) {
						Long no = myService.findUserNoByUserId(userId);
						if (no != null) {
							session.setAttribute("userNo", no);
							return no;
						}
					}
				}
			}
		} catch (Throwable ignore) {
		}

		// ⛔ 더 이상 폴백 없음 (null 반환 → 호출부에서 /login 으로 리다이렉트)
		return null;
	}

	// ================== 기타 helper ==================
	private String withCacheBuster(String url) {
		if (url == null)
			url = "/uploadFilesFinal/default-avatar.png";
		return url + (url.contains("?") ? "&" : "?") + "v=" + System.currentTimeMillis();
	}

	private String toAgeRange(Integer age) {
		if (age == null)
			return "미상";
		int a = age;
		if (a < 20)
			return "10대";
		if (a < 30)
			return "20대";
		if (a < 40)
			return "30대";
		if (a < 50)
			return "40대";
		return "50대 이상";
	}

	private String toGradeName(String code) {
		if (code == null)
			return "일반";
		return switch (code) {
		case "A" -> "A등급";
		case "B" -> "B등급";
		case "C" -> "C등급";
		case "D" -> "D등급";
		
		default -> "D등급";
		};
	}

	private String emptyToNull(String s) {
		return (s == null || s.isBlank()) ? null : s.trim();
	}
	
	
	@GetMapping("/myCoupon")
    public String myCoupon() { return "my/myCoupon"; }

    @GetMapping("/myPoint")
    public String myPoint() { return "my/myPoint"; }

    @GetMapping("/myReview")
    public String myReview() { return "my/myReview"; }

   

    @GetMapping("/myInquiry")
    public String myInquiry() { return "my/myInquiry"; }

    @GetMapping("/myWriteReview")
    public String myWriteReview() { return "my/myWriteReview"; }

  
    @GetMapping("/cart")
    public String cart() { return "Shopping/cart"; }

    @GetMapping("/wishList")
    public String wishList() { return "Shopping/WishList"; }
	
    @GetMapping("/experienceApplication")
    public String experienceApplication(HttpSession session) {
       // if (getUserNo(session) == null) return "redirect:/"; // 로그인 체크가 필요 없다면 이 줄 삭제
        return "my/experienceApplication"; // => templates/my/experienceApplication.html
    }
	
    @GetMapping("/experienceWins")
    public String experienceWins() {return "my/experienceWins"; }
	
 // 상품 검색(AJAX)
    @GetMapping("/searchProduct")
    @ResponseBody
    public List<Product> searchProduct(@RequestParam("keyword") String keyword) {
        return myService.searchProducts(keyword);
    }

 // 리뷰 저장(폼 제출)
    @PostMapping("/review")
    public String saveReview(@ModelAttribute Review review,
                             HttpSession session,
                             RedirectAttributes ra) {
        // ✅ 세션에서 안전하게 꺼내기
        Object attr = session.getAttribute("userNo");
        Long userNo = null;
        if (attr instanceof Number n) {
            userNo = n.longValue();
        }
        if (userNo == null) {
            ra.addFlashAttribute("msg", "로그인이 필요합니다.");
            return "redirect:/go/login";
        }

        // Review.userNo 는 int 이므로 intValue로 세팅
        review.setUserNo(userNo.intValue());

        int rows = myService.insertReview(review);
        ra.addFlashAttribute("msg", rows > 0 ? "리뷰가 등록되었습니다." : "리뷰 등록 실패");
        return "redirect:/my/myReview";
    }
    
    
    
    
    
}
