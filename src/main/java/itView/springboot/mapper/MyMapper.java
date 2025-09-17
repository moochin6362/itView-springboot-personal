package itView.springboot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import itView.springboot.vo.ExperienceGroup;
import itView.springboot.vo.Order;
import itView.springboot.vo.PointBox;
import itView.springboot.vo.Review;
import itView.springboot.vo.User;

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

    List<java.util.Map<String,Object>> searchProducts(@Param("keyword") String keyword);

    int insertReview(Review review);

    int countByUserName(@Param("userName") String userName,
                        @Param("excludeUserNo") Long excludeUserNo);

    List<Map<String,Object>> selectMyReviews(@Param("userNo") long userNo);

    // ===== 포인트 (POINT_BOX) =====
    int insertPointBox(PointBox p);
    Integer selectPointBalanceBox(@Param("userNo") long userNo);
    List<PointBox> selectPointHistoryBox(@Param("userNo") long userNo);

    // 쿠폰/찜/경험단 등 기존
    java.util.List<itView.springboot.vo.Coupon> selectMyCoupons(@Param("userNo") long userNo);
    java.util.List<java.util.Map<String, Object>> selectTop3WishlistMap(@Param("userNo") long userNo);

    List<ExperienceGroup> searchExperienceGroups(@Param("keyword") String keyword);

    int countMyExperienceApply(@Param("userNo") Long userNo, @Param("expNo") int expNo);

    int insertExperienceApply(@Param("userNo") Long userNo,
                              @Param("expNo") int expNo,
                              @Param("applyContent") String applyContent,
                              @Param("receiver") String receiver,
                              @Param("phone") String phone,
                              @Param("address") String address,
                              @Param("requestMemo") String requestMemo);

    List<Map<String,Object>> selectExperienceWins(@Param("userNo") long userNo);

    // 서연
    Order selectproductbyOrder(@Param("productNo") Integer pNo, @Param("userNo") int uNo,@Param("orderNo")int oNo);
    itView.springboot.vo.Attachment selectThumbByOrder(Integer pNo);

    // 문의추가
    int insertQuestion(itView.springboot.vo.Question q);

    List<Map<String, Object>> selectOrderedProductsWithAttributes(Long userNo);
    
    ExperienceGroup selectExperienceByNo(@org.apache.ibatis.annotations.Param("expNo") int expNo);

}
