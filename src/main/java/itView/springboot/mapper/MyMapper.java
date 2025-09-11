package itView.springboot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import itView.springboot.vo.Attachment;
import itView.springboot.vo.Order;
import itView.springboot.vo.Product;
import itView.springboot.vo.Review;
import itView.springboot.vo.User;
import itView.springboot.vo.ExperienceGroup;

@Mapper
public interface MyMapper {

    User selectUserByNo(@Param("userNo") Long userNo);

    itView.springboot.vo.Attachment selectProfileImageByUser(@Param("userNo") Long userNo);
    int disableOldProfileImages(@Param("userNo") Long userNo);
    int insertProfileImage(itView.springboot.vo.Attachment attm);

    int updateUserBasicAndSkin(User u);

    int existsUser(@Param("userNo") Long userNo);
    Long selectFirstUserNo();
    Long selectUserNoByUserId(@Param("userId") String userId);

    List<Product> searchProducts(@Param("keyword") String keyword);

    int insertReview(Review review);

    int countByUserName(@Param("userName") String userName,
                        @Param("excludeUserNo") Long excludeUserNo);

    List<Map<String,Object>> selectMyReviews(@Param("userNo") long userNo);

    // 포인트 잔액 (USER.USER_POINT)
    Integer selectPointBalance(@Param("userNo") long userNo);

    // 포인트 내역 (POINT)
    java.util.List<itView.springboot.vo.Point> selectPointHistory(@Param("userNo") long userNo);

    // 보유 쿠폰(사용 가능) 목록
    java.util.List<itView.springboot.vo.Coupon> selectMyCoupons(@Param("userNo") long userNo);

    // 최근 찜 3개
    java.util.List<java.util.Map<String, Object>> selectTop3WishlistMap(@Param("userNo") long userNo);

    List<ExperienceGroup> searchExperienceGroups(@Param("keyword") String keyword);

    // 중복 신청 체크 / 저장
    int countMyExperienceApply(@Param("userNo") Long userNo, @Param("expNo") int expNo);
    int insertExperienceApply(@Param("userNo") Long userNo, @Param("expNo") int expNo, @Param("applyContent") String applyContent);

    List<Map<String,Object>> selectExperienceWins(@Param("userNo") long userNo);

    // 서연
    Order selectproductbyOrder(@Param("productNo") Integer pNo, @Param("userNo") int uNo);
    itView.springboot.vo.Attachment selectThumbByOrder(Integer pNo);
}
