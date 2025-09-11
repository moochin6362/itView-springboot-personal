package itView.springboot.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Order;
import itView.springboot.vo.Product;
import itView.springboot.vo.Review;
import itView.springboot.vo.User;
import itView.springboot.vo.ExperienceGroup;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController {

    private final MyService myService;
    private static final String LOGIN_URL = "/";

    /** 마이페이지(프로필/연령대/등급 표시) */
    @GetMapping("/myPage")
    public String myPage(HttpSession session, Model model) {
        Long userNo = getUserNo(session);
        if (userNo == null) return "redirect:/";

        User u = myService.getUser(userNo);
        if (u != null) {
            model.addAttribute("profileName", u.getUserName());

            String ageRange = "연령대 미상";
            LocalDate birth = u.getUserAge(); // LocalDate
            if (birth != null) {
                ageRange = toAgeRange(birth);
            }
            model.addAttribute("profileAge", ageRange);
            model.addAttribute("profileGrade", toGradeName(u.getUserGrade()));
        } else {
            model.addAttribute("profileName", "로그인 사용자");
            model.addAttribute("profileAge", "연령대 미상");
            model.addAttribute("profileGrade", "일반");
        }

        String url = myService.getProfileImageUrl(userNo);
        model.addAttribute("profileImageUrl", withCacheBuster(url));

        var wish3 = myService.getRecentWishlistMap(userNo);
        model.addAttribute("wish3", wish3);

        return "my/myPage";
    }

    /** 프로필 이미지 업로드(교체) */
    @PostMapping("/profile-image")
    public String uploadProfile(@RequestParam("file") MultipartFile file,
                                HttpSession session,
                                RedirectAttributes ra) {
        Long userNo = getUserNo(session);
        if (userNo == null) {
            ra.addFlashAttribute("msg", "세션이 만료되었습니다. 다시 로그인해주세요.");
            return "redirect:/";
        }
        try {
            myService.updateProfileImage(userNo, file);
            ra.addFlashAttribute("msg", "프로필이 변경되었습니다.");
        } catch (Exception e) {
            log.error("프로필 변경 실패", e);
            ra.addFlashAttribute("msg", "프로필 변경 실패: " + e.getMessage());
        }
        return "redirect:/my/myInfo";
    }

    /** 내 정보 보기 */
    @GetMapping("/myInfo")
    public String myInfo(HttpSession session, Model model) {
        Long userNo = getUserNo(session);
        if (userNo == null) return "redirect:/";

        // 1) 프로필 이미지
        String url = myService.getProfileImageUrl(userNo);
        model.addAttribute("profileImageUrl", withCacheBuster(url));

        // 2) 사용자 정보
        User u = myService.getUser(userNo);
        model.addAttribute("user", u);

        // 3) 화면 라디오 체크용 "연령대 밴드"(10/20/30…)
        Integer ageBand = null;
        if (u != null && u.getUserAge() != null) {
            ageBand = toAgeBand(u.getUserAge());
        }
        model.addAttribute("ageBand", ageBand);

        // 4) 체크박스(피부고민) 리스트
        model.addAttribute("concernList", splitCsv(u != null ? u.getSkinTrouble() : null));

        return "my/myInfo";
    }

    private java.util.List<String> splitCsv(String s) {
        if (s == null || s.isBlank()) return java.util.Collections.emptyList();
        return java.util.Arrays.stream(s.split("\\s*,\\s*"))
                .filter(t -> !t.isBlank())
                .toList();
    }

    /** 내 정보 저장 (USER_AGE는 DATE(생년)로 저장) */
    @PostMapping("/info")
    public String saveInfo(@RequestParam(name = "email",         required = false) String email,
                           @RequestParam(name = "nickname",      required = false) String userName,
                           @RequestParam(name = "gender",        required = false) String userGender,
                           @RequestParam(name = "ageRange",      required = false) Integer ageRange, // 표시용(저장X)
                           @RequestParam(name = "skinType",      required = false) String skinType,
                           @RequestParam(name = "personalColor", required = false) String personalColor,
                           @RequestParam(name = "concerns",      required = false) String[] concerns,
                           @RequestParam(name = "headSkin",      required = false) String headSkin,
                           @RequestParam(name = "hopePrice",     required = false) String hopePrice,
                           @RequestParam(name = "ingredient",    required = false) String ingredient,
                           @RequestParam(name = "ecoFriendly",   required = false) String ecoFriendly,
                           @RequestParam(name = "birthDate",     required = false)
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate,
                           HttpSession session,
                           RedirectAttributes ra) {

        Long userNo = getUserNo(session);
        if (userNo == null) {
            ra.addFlashAttribute("msg", "세션이 만료되었습니다. 다시 로그인해주세요.");
            return "redirect:/go/login";
        }

        // 1) 모든 필드 세팅
        User u = new User();
        u.setUserNo(Math.toIntExact(userNo));
        u.setEmail(emptyToNull(email));
        u.setUserName(emptyToNull(userName));
        u.setUserGender(emptyToNull(userGender));

        // 생년(실제 DB USER_AGE: DATE)
        if (birthDate != null) {
            u.setUserAge(birthDate);
        }

        // 피부/선호
        u.setSkinType(emptyToNull(skinType));
        u.setPersonalColor(emptyToNull(personalColor));
        u.setSkinTrouble((concerns != null && concerns.length > 0) ? String.join(",", concerns) : null);
        u.setHeadSkin(emptyToNull(headSkin));

        // 신규 추가 필드
        u.setHopePrice(emptyToNull(hopePrice));     // priceRange1~4
        u.setIngredient(emptyToNull(ingredient));   // natural/vitamin/peptide/moisture/cleansing
        u.setEcoFriendly(emptyToNull(ecoFriendly)); // 'Y' / 'N'

        // 2) 딱 한 번 업데이트
        int rows = myService.updateUserBasicAndSkin(u);

        ra.addFlashAttribute("msg", rows > 0 ? "내 정보가 저장되었습니다." : "저장할 변경 사항이 없습니다.");
        return "redirect:/my/myPage";
    }


    // ================== userNo 획득 ==================
    private Long getUserNo(HttpSession session) {
        Object o = session.getAttribute("userNo");
        if (o instanceof Number) return ((Number) o).longValue();

        try {
            Object loginUser = session.getAttribute("loginUser");
            if (loginUser instanceof User lu && lu.getUserNo() > 0) {
                Long no = (long) lu.getUserNo();
                session.setAttribute("userNo", no);
                return no;
            }
        } catch (Throwable ignore) {}

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
        } catch (Throwable ignore) {}
        return null;
    }

    // =============== helper ===============
    private Integer toAgeBand(java.util.Date birth) {
        if (birth == null) return null;
        LocalDate b = (birth instanceof java.sql.Date sd)
                ? sd.toLocalDate()
                : birth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int age = Period.between(b, LocalDate.now()).getYears();
        if (age < 10) return null;
        return (age / 10) * 10;
    }

    private Integer toAgeBand(LocalDate birth) {
        if (birth == null) return null;
        int age = Period.between(birth, LocalDate.now()).getYears();
        if (age < 10) return null;
        return (age / 10) * 10;
    }

    private String withCacheBuster(String url) {
        if (url == null) url = "/uploadFilesFinal/default-avatar.png";
        return url + (url.contains("?") ? "&" : "?") + "v=" + System.currentTimeMillis();
    }

    private String toAgeRange(LocalDate birth) {
        if (birth == null) return "미상";
        int age = Period.between(birth, LocalDate.now()).getYears();
        if (age < 10) return "미상";
        int band = (age / 10) * 10;
        if (band >= 50) return "50대 이상";
        return band + "대";
    }

    private String toGradeName(String code) {
        if (code == null) return "일반";
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
    public String myCoupon(HttpSession session, Model model) {
        Long userNo = getUserNo(session);
        if (userNo == null) return "redirect:/";

        var coupons = myService.getMyCoupons(userNo);
        model.addAttribute("coupons", coupons != null ? coupons : java.util.Collections.emptyList());
        return "my/myCoupon";
    }

    //서연
    @GetMapping("/myReview")
    public String myReview(@RequestParam(value = "orderNo", required = false) Integer oNo,
                           @RequestParam(value = "productNo", required = false) Integer pNo,
                           Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";

        int uNo = loginUser.getUserNo();
        if (pNo != null) {
            Order order = myService.selectproductbyOrder(pNo, uNo);
            Attachment attachment = myService.selectThumbByOrder(pNo);
            model.addAttribute("order", order);
            model.addAttribute("attachment", attachment);
        }
        return "my/myReview";
    }

    @GetMapping("/myInquiry")
    public String myInquiry() { return "my/myInquiry"; }

    @GetMapping("/myProductInquiry")
    public String myProductInquiry() { return "my/myProductInquiry"; }

    @GetMapping("/cart")
    public String cart() { return "Shopping/cart"; }

    @GetMapping("/WishList")
    public String wishList() { return "Shopping/WishList"; }

    // 내가 쓴 리뷰
    @GetMapping("/myWriteReview")
    public String myWriteReview(HttpSession session, Model model) {
        Long userNo = getUserNo(session);
        if (userNo == null) return "redirect:/";
        List<java.util.Map<String, Object>> myReviews = myService.getMyReviewsMap(userNo);
        model.addAttribute("myReviews", myReviews);
        return "my/myWriteReview";
    }

    // 상품 검색(AJAX)
    @GetMapping("/searchProduct")
    @ResponseBody
    public List<Product> searchProduct(@RequestParam("keyword") String keyword) {
        return myService.searchProducts(keyword);
    }

    // 리뷰 저장
    @PostMapping("/review")
    public String saveReview(@ModelAttribute Review review,
                             HttpSession session,
                             RedirectAttributes ra) {
        Object attr = session.getAttribute("userNo");
        Long userNo = (attr instanceof Number n) ? n.longValue() : null;
        if (userNo == null) {
            ra.addFlashAttribute("msg", "로그인이 필요합니다.");
            return "redirect:/go/login";
        }
        review.setUserNo(userNo.intValue());
        int rows = myService.insertReview(review);
        ra.addFlashAttribute("msg", rows > 0 ? "리뷰가 등록되었습니다." : "리뷰 등록 실패");
        return "redirect:/my/myReview";
    }

    @GetMapping("/experienceApplication")
    public String experienceApplication(HttpSession session) {
        return "my/experienceApplication";
    }

    // 체험단 당첨 내역
    @GetMapping("/experienceWins")
    public String experienceWins(HttpSession session, Model model) {
        Long userNo = getUserNo(session);
        if (userNo == null) return "redirect:/";
        List<java.util.Map<String, Object>> wins = myService.getExperienceWins(userNo);
        model.addAttribute("winList", wins != null ? wins : java.util.Collections.emptyList());
        return "my/experienceWins";
    }

    @GetMapping("/myPoint")
    public String myPoint(HttpSession session, Model model) {
        Long userNo = getUserNo(session);
        if (userNo == null) return "redirect:/";

        int balance = myService.getPointBalance(userNo); // ← USER.USER_POINT 사용
        java.util.List<itView.springboot.vo.Point> list = myService.getPointHistory(userNo); // 내역은 POINT

        model.addAttribute("pointBalance", balance);
        model.addAttribute("expireSoonText", "각 적립건의 만료일을 확인해주세요.");
        model.addAttribute("histories", (list != null ? list : java.util.Collections.emptyList()));

        return "my/myPoint";
    }

    @GetMapping("/experience/search")
    @ResponseBody
    public java.util.List<ExperienceGroup> searchExperience(@RequestParam(name = "q", required = false) String q) {
        String keyword = (q == null) ? "" : q.trim();
        return myService.searchExperienceGroups(keyword);
    }

    // 체험단 신청 저장
    @PostMapping("/experience-apply")
    @ResponseBody
    public java.util.Map<String,Object> applyExperience(@RequestParam("expNo") int expNo,
                                                        @RequestParam(name="applyContent", required=false) String applyContent,
                                                        HttpSession session) {
        Long userNo = getUserNo(session);
        if (userNo == null) {
            return java.util.Map.of("ok", false, "message", "로그인이 필요합니다.");
        }

        int already = myService.countMyExperienceApply(userNo, expNo);
        if (already > 0) {
            return java.util.Map.of("ok", false, "message", "이미 신청한 모집글입니다.");
        }

        int rows = myService.insertExperienceApply(userNo, expNo, applyContent);
        return java.util.Map.of("ok", rows > 0);
    }
}
