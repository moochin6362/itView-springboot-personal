package itView.springboot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import itView.springboot.vo.Attachment;
import itView.springboot.vo.Product;
import itView.springboot.vo.Review;
import itView.springboot.vo.User;

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
}
