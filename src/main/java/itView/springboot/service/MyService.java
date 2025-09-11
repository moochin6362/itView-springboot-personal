package itView.springboot.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import itView.springboot.mapper.MyMapper;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Order;
import itView.springboot.vo.Product;
import itView.springboot.vo.Review;
import itView.springboot.vo.User;
import itView.springboot.vo.ExperienceGroup;
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
        if (a == null) return WEB_BASE + "default-avatar.png";
        return (a.getAttmPath() != null ? a.getAttmPath() : WEB_BASE) + a.getAttmRename();
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
        attm.setAttmPath(WEB_BASE);
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
    public List<Product> searchProducts(String keyword) {
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
    public int insertExperienceApply(Long userNo, int expNo, String applyContent) {
        return myMapper.insertExperienceApply(userNo, expNo, applyContent);
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
}
