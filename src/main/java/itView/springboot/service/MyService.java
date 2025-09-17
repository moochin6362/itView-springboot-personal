package itView.springboot.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import itView.springboot.mapper.MyMapper;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.ExperienceGroup;
import itView.springboot.vo.Order;
import itView.springboot.vo.PointBox;
import itView.springboot.vo.Question;
import itView.springboot.vo.Review;
import itView.springboot.vo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyService {

    private final MyMapper myMapper;

    private static final String DISK_DIR = "c:/uploadFilesFinal/notice/";
    private static final String WEB_BASE = "/uploadFilesFinal/";

    public User getUser(Long userNo) {
        return myMapper.selectUserByNo(userNo);
    }

    public String getProfileImageUrl(Long userNo) {
        Attachment a = myMapper.selectProfileImageByUser(userNo);
        if (a == null) return "/default-avatar.png";
        String base = (a.getAttmPath() != null && !a.getAttmPath().isBlank()) ? a.getAttmPath() : "/";
        if (!base.endsWith("/")) base += "/";
        return base + a.getAttmRename();
    }

    @Transactional
    public void updateProfileImage(Long userNo, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("파일이 비어있습니다.");

        File dir = new File(DISK_DIR);
        if (!dir.exists() && !dir.mkdirs()) throw new IOException("폴더 생성 실패: " + DISK_DIR);

        String origin = file.getOriginalFilename();
        String ext = "";
        if (origin != null && origin.lastIndexOf('.') > -1) {
            ext = origin.substring(origin.lastIndexOf('.')).toLowerCase();
        }
        String rename = System.currentTimeMillis() + "_" +
                UUID.randomUUID().toString().replace("-", "").substring(0, 8) + ext;

        file.transferTo(new File(DISK_DIR + rename));

        myMapper.disableOldProfileImages(userNo);

        Attachment attm = new Attachment();
        attm.setAttmName(origin);
        attm.setAttmRename(rename);
        attm.setAttmPath("/");
        attm.setAttmStatus("Y");
        attm.setAttmLevel(0);
        attm.setAttmPosition(7);
        attm.setPositionNo(Math.toIntExact(userNo));

        myMapper.insertProfileImage(attm);
        log.info("프로필 교체 완료 userNo={}, rename={}", userNo, rename);
    }

    @Transactional
    public int updateUserBasicAndSkin(User u) {
        int rows = myMapper.updateUserBasicAndSkin(u);
        log.info("updateUserBasicAndSkin userNo={}, affectedRows={}", u.getUserNo(), rows);
        return rows;
    }

    public Long findUserNoByUserId(String userId) {
        if (userId == null || userId.isBlank()) return null;
        return myMapper.selectUserNoByUserId(userId);
    }

    /** 상품 검색 */
    public java.util.List<java.util.Map<String,Object>> searchProducts(String keyword) {
        if (keyword == null || keyword.isBlank()) return java.util.List.of();
        return myMapper.searchProducts(keyword.trim());
    }

    /** 리뷰 저장 */
    @Transactional
    public int insertReview(Review review) {
        if (review == null) return 0;
        if (review.getProductNo() <= 0 || review.getUserNo() <= 0 || review.getReviewRate() <= 0) {
            return 0;
        }
        return myMapper.insertReview(review);
    }

    public int countNickname(String nickname, Long excludeUserNo) {
        if (nickname == null || nickname.isBlank()) return Integer.MAX_VALUE;
        return myMapper.countByUserName(nickname.trim(), excludeUserNo);
    }

    public List<Map<String,Object>> getMyReviewsMap(long userNo) {
        return myMapper.selectMyReviews(userNo);
    }

    // ===== 포인트 (POINT_BOX 기준) =====
    /** 잔액: POINT_BOX의 합계(적립+ / 차감-) */
    public int getPointBalance(long userNo) {
        Integer v = myMapper.selectPointBalanceBox(userNo);
        return v == null ? 0 : v;
    }

    /** 내역: POINT_BOX 행 자체 */
    public java.util.List<PointBox> getPointHistory(long userNo) {
        return myMapper.selectPointHistoryBox(userNo);
    }

    /** 지급/차감 1건 기록 */
    @Transactional
    public void addPointBox(long userNo, String name, String description, int value, LocalDate endDate) {
        PointBox p = new PointBox();
        p.setUserNo((int) userNo);
        p.setPointName(name);
        p.setPointDescription(description);
        p.setPointValue(value); // +적립 / -차감
        p.setPointEnddate(endDate);
        myMapper.insertPointBox(p);
    }

    public java.util.List<itView.springboot.vo.Coupon> getMyCoupons(long userNo) {
        return myMapper.selectMyCoupons(userNo);
    }

    public java.util.List<java.util.Map<String,Object>> getRecentWishlistMap(long userNo) {
        return myMapper.selectTop3WishlistMap(userNo);
    }

    /** 모집글 검색 */
    public java.util.List<ExperienceGroup> searchExperienceGroups(String keyword) {
        String kw = (keyword == null) ? "" : keyword.trim();
        return myMapper.searchExperienceGroups(kw);
    }

    // 중복 신청 여부
    public int countMyExperienceApply(Long userNo, int expNo) {
        return myMapper.countMyExperienceApply(userNo, expNo);
    }

    // 신청 저장
    public int insertExperienceApply(Long userNo, int expNo, String applyContent,
                                     String receiver, String phone, String address, String requestMemo) {
        return myMapper.insertExperienceApply(userNo, expNo, applyContent, receiver, phone, address, requestMemo);
    }

    public List<Map<String,Object>> getExperienceWins(long userNo) {
        return myMapper.selectExperienceWins(userNo);
    }

    //서연
    public Order selectproductbyOrder(Integer pNo, int uNo,int oNo) {
        return myMapper.selectproductbyOrder(pNo, uNo,oNo);
    }

    //서연
    public itView.springboot.vo.Attachment selectThumbByOrder(Integer pNo) {
        return myMapper.selectThumbByOrder(pNo);
    }

    // 매칭률 (기존 그대로)
    public List<Map<String,Object>> getMatchingRates(Long userNo) {
        User u = getUser(userNo);
        List<Map<String,Object>> rows = myMapper.selectOrderedProductsWithAttributes(userNo);

        String uSkin   = nz(u.getSkinType());
        String uGender = nz(u.getUserGender());
        String uTroubleCsv = nz(u.getSkinTrouble());
        String uColor  = nz(u.getPersonalColor());
        Integer uAge10 = (u.getUserAge() != null) ? toAgeBand(u.getUserAge()) : null;

        for (Map<String,Object> p : rows) {
            String pSkinKo   = nz(getStr(p, "skinType",   "SKINTYPE"));
            String pGender   = nz(getStr(p, "targetGender","TARGETGENDER"));
            String pAgeTxt   = nz(getStr(p, "targetAge",  "TARGETAGE"));
            String pTrouble  = nz(getStr(p, "skinTrouble","SKINTROUBLE"));
            String pColorTxt = nz(getStr(p, "personalColor","PERSONALCOLOR"));

            int wSkin = 30, wAge = 20, wTrouble = 20, wGender = 20, wColor = 10;
            int wTotal = wSkin + wAge + wTrouble + wGender + wColor;
            int score = 0;

            if (!uSkin.isEmpty() && !pSkinKo.isEmpty()) {
                if (matchSkin(uSkin, pSkinKo)) score += wSkin;
            } else { wTotal -= wSkin; }

            if (uAge10 != null && !pAgeTxt.isEmpty()) {
                if (pAgeTxt.contains("모든") || pAgeTxt.contains("전체") || pAgeTxt.contains(uAge10 + "대")) score += wAge;
            } else { wTotal -= wAge; }

            if (!uTroubleCsv.isEmpty() && !pTrouble.isEmpty()) {
                if (matchTrouble(uTroubleCsv, pTrouble)) score += wTrouble;
            } else { wTotal -= wTrouble; }

            if (!uGender.isEmpty() && !pGender.isEmpty()) {
                if (pGender.equals("A") || pGender.equals(uGender)) score += wGender;
            } else { wTotal -= wGender; }

            if (!uColor.isEmpty() && !pColorTxt.isEmpty()) {
                if (pColorTxt.contains("해당 없음") || matchPersonalColor(uColor, pColorTxt)) score += wColor;
            } else { wTotal -= wColor; }

            int rate = (wTotal > 0) ? (int)Math.round(score * 100.0 / wTotal) : 0;
            p.put("MATCH_RATE", rate);

            String name = nz(getStr(p, "productName","PRODUCTNAME"));
            p.put("LABEL", (name.length() > 20) ? name.substring(0, 20) + "…" : name);
        }
        return rows;
    }

    // ====================== 헬퍼 ======================
    private String nz(String s) {
        if (s == null) return "";
        String t = s.trim();
        return "null".equalsIgnoreCase(t) ? "" : t;
    }

    private Integer toAgeBand(LocalDate birth) {
        if (birth == null) return null;
        int age = java.time.Period.between(birth, java.time.LocalDate.now()).getYears();
        if (age < 10) return null;
        return (age / 10) * 10;
    }

    private String normalizeProductSkin(String pSkinKo){
        String s = nz(pSkinKo);
        if (s.contains("공용")) return "ANY";
        if (s.contains("지성")) return "OILY";
        if (s.contains("건성")) return "DRY";
        if (s.contains("복합")) return "COMBI";
        if (s.contains("민감")) return "SENSITIVE";
        if (s.contains("수부지")) return "DEHYDRATED";
        return "";
    }

    private boolean matchSkin(String userSkinCode, String productSkinKo){
        String u = nz(userSkinCode).toUpperCase();
        if (u.isEmpty()) return false;
        String p = normalizeProductSkin(productSkinKo);
        if ("ANY".equals(p)) return true;
        return u.equalsIgnoreCase(p);
    }

    private java.util.Set<String> troubleKeywords(String userCsv){
        java.util.Set<String> set = new java.util.HashSet<>();
        for (String raw : userCsv.split("\\s*,\\s*")) {
            String t = nz(raw).toUpperCase();
            if (t.isEmpty()) continue;
            switch (t) {
                case "WRINKLE", "ELASTICITY" -> set.add("주름");
                case "PORE", "BLACKHEAD", "SEBUM" -> { set.add("모공"); set.add("블랙헤드"); set.add("피지"); }
                case "WHITENING", "SPOT", "BLEMISH" -> { set.add("미백"); set.add("잡티"); }
                case "DRYNESS", "DEHYDRATION" -> { set.add("건조"); set.add("속건조"); }
                case "SENSITIVITY", "IRRITATION" -> set.add("민감");
                case "ACNE", "PIMPLE" -> set.add("여드름");
                default -> set.add(raw);
            }
        }
        return set;
    }

    private boolean matchTrouble(String userCsv, String productText){
        String csv = nz(userCsv);
        String txt = nz(productText);
        if (csv.isEmpty() || txt.isEmpty()) return false;
        String compact = txt.replace(" ", "");
        for (String kw : troubleKeywords(csv)) {
            if (compact.contains(kw)) return true;
        }
        return false;
    }

    private boolean matchPersonalColor(String userColorCode, String productText){
        String u = nz(userColorCode).toUpperCase();
        String p = nz(productText);
        if (p.isEmpty() || u.isEmpty()) return false;
        if (p.contains("해당 없음")) return true;
        return switch (u) {
            case "SPRING" -> p.contains("봄");
            case "SUMMER" -> p.contains("여름");
            case "FALL", "AUTUMN" -> p.contains("가을");
            case "WINTER" -> p.contains("겨울");
            default -> false;
        };
    }

    private boolean matchAge(Integer userAge10, String productAgeKo){
        if (userAge10 == null) return false;
        String t = nz(productAgeKo);
        return t.contains("모든") || t.contains("전체") || t.contains(userAge10 + "대");
    }

    private String getStr(Map<String,Object> m, String... keys) {
        for (String k : keys) {
            Object v = m.get(k);
            if (v != null) return String.valueOf(v);
        }
        return "";
    }
    
    @org.springframework.transaction.annotation.Transactional
    public int insertQuestion(Question q) {
        if (q == null) return 0;
        if (q.getUserNo() <= 0 || q.getProductNo() <= 0) return 0;

        // 필요 시 서버 검증 (구매자만 문의 허용) — 잠시 비활성화
        // Order owned = myMapper.selectproductbyOrder(q.getProductNo(), q.getUserNo());
        // if (owned == null) return 0;

        if (q.getQuestionStatus() == null || q.getQuestionStatus().isBlank()) {
            q.setQuestionStatus("N");
        }
        return myMapper.insertQuestion(q);
    }
    
 // MyService.java (일부분에 추가)
    public ExperienceGroup getExperienceByNo(int expNo) {
        return myMapper.selectExperienceByNo(expNo);
    }

    
    
}
