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
        if (a == null) return "/default-avatar.png";   // ✅ '/notice/' 제거

        String base = (a.getAttmPath() != null && !a.getAttmPath().isBlank())
                ? a.getAttmPath()
                : "/";                                // ✅ 루트 기준
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
        // ✅ '/notice/' → '/' 로 변경 (루트 기준 URL만 저장)
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

    // ===== 포인트 =====
    /** 보유 포인트: USER.USER_POINT */
    public int getPointBalance(long userNo) {
        Integer v = myMapper.selectPointBalance(userNo); // XML에서 USER_POINT를 반환
        return v == null ? 0 : v;
    }

    /** 포인트 내역: POINT 테이블 */
    public java.util.List<itView.springboot.vo.Point> getPointHistory(long userNo) {
        return myMapper.selectPointHistory(userNo);
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
 // MyService.java
    public int insertExperienceApply(Long userNo, int expNo, String applyContent,
                                     String receiver, String phone, String address, String requestMemo) {
        return myMapper.insertExperienceApply(userNo, expNo, applyContent, receiver, phone, address, requestMemo);
    }


    public List<Map<String,Object>> getExperienceWins(long userNo) {
        return myMapper.selectExperienceWins(userNo);
    }

    //서연
    public Order selectproductbyOrder(Integer pNo, int uNo) {
        return myMapper.selectproductbyOrder(pNo, uNo);
    }

    //서연
    public itView.springboot.vo.Attachment selectThumbByOrder(Integer pNo) {
        return myMapper.selectThumbByOrder(pNo);
    }
    
 // MyService.java (추가)
    @Transactional
    public int insertQuestion(itView.springboot.vo.Question q) {
        if (q == null) return 0;
        if (q.getUserNo() <= 0 || q.getProductNo() <= 0) return 0;

        // 서버 검증: 해당 상품을 실제로 주문한 유저만 문의 가능
        Order owned = myMapper.selectproductbyOrder(q.getProductNo(), q.getUserNo());
        if (owned == null) return 0;

        if (q.getQuestionStatus() == null || q.getQuestionStatus().isBlank()) {
            q.setQuestionStatus("N");
        }
        return myMapper.insertQuestion(q);
    }

 // 매칭률

    public List<Map<String,Object>> getMatchingRates(Long userNo) {
        User u = getUser(userNo);

        // 주문한 상품 + 상품 속성 (이미 잘 나오는 쿼리)
        List<Map<String,Object>> rows = myMapper.selectOrderedProductsWithAttributes(userNo);

        // 사용자 기준값
        String uSkin   = nz(u.getSkinType());        // OILY/DRY/COMBI/SENSITIVE/DEHYDRATED
        String uGender = nz(u.getUserGender());      // M/F
        String uTroubleCsv = nz(u.getSkinTrouble()); // CSV일 수 있음
        String uColor  = nz(u.getPersonalColor());   // SPRING/SUMMER/FALL/WINTER/UNKNOWN
        Integer uAge10 = (u.getUserAge() != null) ? toAgeBand(u.getUserAge()) : null; // 10/20/30…

        for (Map<String,Object> p : rows) {
            // ── 상품 측 속성 (키 이름은 /my/test-matching 에서 온 그대로 사용)
        	String pSkinKo   = nz(getStr(p, "skinType",   "SKINTYPE"));
        	String pGender   = nz(getStr(p, "targetGender","TARGETGENDER"));
        	String pAgeTxt   = nz(getStr(p, "targetAge",  "TARGETAGE"));
        	String pTrouble  = nz(getStr(p, "skinTrouble","SKINTROUBLE"));
        	String pColorTxt = nz(getStr(p, "personalColor","PERSONALCOLOR"));

            // ── 가중치 (원하면 숫자만 바꾸면 됨)
            int wSkin = 30, wAge = 20, wTrouble = 20, wGender = 20, wColor = 10;
            int wTotal = wSkin + wAge + wTrouble + wGender + wColor;
            int score = 0;

            // 1) 피부타입
            if (!uSkin.isEmpty() && !pSkinKo.isEmpty()) {
                if (matchSkin(uSkin, pSkinKo)) score += wSkin;
            } else { wTotal -= wSkin; }

            // 2) 연령대
            if (uAge10 != null && !pAgeTxt.isEmpty()) {
                if (pAgeTxt.contains("모든") || pAgeTxt.contains("전체") || pAgeTxt.contains(uAge10 + "대")) score += wAge;
            } else { wTotal -= wAge; }

            // 3) 피부고민 (유저 CSV 중 하나라도 포함되면 매칭)
            if (!uTroubleCsv.isEmpty() && !pTrouble.isEmpty()) {
                if (matchTrouble(uTroubleCsv, pTrouble)) score += wTrouble;
            } else { wTotal -= wTrouble; }

            // 4) 성별 (A=공용)
            if (!uGender.isEmpty() && !pGender.isEmpty()) {
                if (pGender.equals("A") || pGender.equals(uGender)) score += wGender;
            } else { wTotal -= wGender; }

            // 5) 퍼스널컬러 (“해당 없음”은 매칭으로 처리)
            if (!uColor.isEmpty() && !pColorTxt.isEmpty()) {
                if (pColorTxt.contains("해당 없음") || matchPersonalColor(uColor, pColorTxt)) score += wColor;
            } else { wTotal -= wColor; }

            int rate = (wTotal > 0) ? (int)Math.round(score * 100.0 / wTotal) : 0;
            //if (rate == 0) rate = 10; // ✅ 최소 10% 보장
            p.put("MATCH_RATE", rate); // ✅ 여기서 JSON에만 필드 추가 (DB 변경 X)

            // 그래프용으로 길어진 이름 잘리도록 짧은 라벨도 실어주면 좋음
            String name = nz(getStr(p, "productName","PRODUCTNAME"));
            p.put("LABEL", (name.length() > 20) ? name.substring(0, 20) + "…" : name);
        }
        return rows;
    }

 // ====================== 헬퍼 ======================

    /** null-safe string: null, 공백, "null" → "" 로 정규화 */
    private String nz(String s) {
        if (s == null) return "";
        String t = s.trim();
        return "null".equalsIgnoreCase(t) ? "" : t;
    }

    /** LocalDate 생년 → 10단위 연령(10/20/30...) */
    private Integer toAgeBand(LocalDate birth) {
        if (birth == null) return null;
        int age = java.time.Period.between(birth, java.time.LocalDate.now()).getYears();
        if (age < 10) return null;
        return (age / 10) * 10;
    }

    /** 상품 한글 SKIN_TYPE → 표준 코드로 정규화 */
    private String normalizeProductSkin(String pSkinKo){
        String s = nz(pSkinKo);
        if (s.contains("공용")) return "ANY";
        if (s.contains("지성")) return "OILY";
        if (s.contains("건성")) return "DRY";
        if (s.contains("복합")) return "COMBI";
        if (s.contains("민감")) return "SENSITIVE";
        if (s.contains("수부지")) return "DEHYDRATED"; // 수분부족지성
        return "";
    }

    /** 유저 피부타입 코드(영문)와 상품 피부타입(한글)의 매칭 여부 */
    private boolean matchSkin(String userSkinCode, String productSkinKo){
        String u = nz(userSkinCode).toUpperCase();
        if (u.isEmpty()) return false;
        String p = normalizeProductSkin(productSkinKo);
        if ("ANY".equals(p)) return true;   // 공용
        return u.equalsIgnoreCase(p);
    }

    /** 유저 고민 CSV(영문/한글 섞임) → 한글 키워드 집합 */
    private java.util.Set<String> troubleKeywords(String userCsv){
        java.util.Set<String> set = new java.util.HashSet<>();
        for (String raw : userCsv.split("\\s*,\\s*")) {
            String t = nz(raw).toUpperCase();
            if (t.isEmpty()) continue;
            switch (t) {
                case "WRINKLE", "ELASTICITY" -> set.add("주름"); // 주름/탄력
                case "PORE", "BLACKHEAD", "SEBUM" -> { set.add("모공"); set.add("블랙헤드"); set.add("피지"); }
                case "WHITENING", "SPOT", "BLEMISH" -> { set.add("미백"); set.add("잡티"); }
                case "DRYNESS", "DEHYDRATION" -> { set.add("건조"); set.add("속건조"); }
                case "SENSITIVITY", "IRRITATION" -> set.add("민감");
                case "ACNE", "PIMPLE" -> set.add("여드름");
                default -> set.add(raw); // 이미 한글일 수 있음
            }
        }
        return set;
    }

    /** 상품 고민 텍스트(한글)에 유저 고민 키워드 하나라도 포함되면 매칭 */
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

    /** 퍼스널컬러 매칭: 상품이 '해당 없음'이면 매칭으로 간주 */
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

    /** 상품 타깃 연령대(한글)에 유저 10단위 연령 포함 여부 */
    private boolean matchAge(Integer userAge10, String productAgeKo){
        if (userAge10 == null) return false;
        String t = nz(productAgeKo);
        return t.contains("모든") || t.contains("전체") || t.contains(userAge10 + "대");
    }

    /** 맵에서 여러 키 후보 중 처음 존재하는 문자열 반환 */
    private String getStr(Map<String,Object> m, String... keys) {
        for (String k : keys) {
            Object v = m.get(k);
            if (v != null) return String.valueOf(v);
        }
        return "";
    }

    
}
