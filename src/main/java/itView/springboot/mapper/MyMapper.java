package itView.springboot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import itView.springboot.vo.Attachment;
import itView.springboot.vo.Product;
import itView.springboot.vo.Review;
import itView.springboot.vo.User;
import itView.springboot.vo.experienceGroup;

@Mapper
public interface MyMapper {

    User selectUserByNo(@Param("userNo") Long userNo);

    Attachment selectProfileImageByUser(@Param("userNo") Long userNo);
    int disableOldProfileImages(@Param("userNo") Long userNo);
    int insertProfileImage(Attachment attm);

    int updateUserBasicAndSkin(User u);

    int existsUser(@Param("userNo") Long userNo);
    Long selectFirstUserNo();
    Long selectUserNoByUserId(@Param("userId") String userId);

    List<Product> searchProducts(@Param("keyword") String keyword);

    int insertReview(Review review);

    int countByUserName(@Param("userName") String userName,
                        @Param("excludeUserNo") Long excludeUserNo);

    List<Map<String,Object>> selectMyReviews(@Param("userNo") long userNo);
    
    // 포인트잔액(이력 합계)
    Integer selectPointBalance(@org.apache.ibatis.annotations.Param("userNo") long userNo);

    // 내역 목록
    java.util.List<itView.springboot.vo.Point> selectPointHistory(@org.apache.ibatis.annotations.Param("userNo") long userNo);

    // 보유 쿠폰(사용 가능) 목록
    java.util.List<itView.springboot.vo.Coupon> selectMyCoupons(
            @org.apache.ibatis.annotations.Param("userNo") long userNo);
    
 // 최근 찜 3개 (+대표 이미지 URL까지)
    java.util.List<java.util.Map<String, Object>> selectTop3WishlistMap(
            @org.apache.ibatis.annotations.Param("userNo") long userNo);

    List<experienceGroup> searchExperienceGroups(@Param("keyword") String keyword);

 // 중복 신청 체크
    int countMyExperienceApply(@Param("userNo") Long userNo,
                               @Param("expNo") int expNo);

    // 신청 저장
    int insertExperienceApply(@Param("userNo") Long userNo,
                              @Param("expNo") int expNo,
                              @Param("applyContent") String applyContent);

    List<Map<String,Object>> selectExperienceWins(@Param("userNo") long userNo);

}
