package itView.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import itView.springboot.vo.Attachment;
import itView.springboot.vo.Product;
import itView.springboot.vo.Review;
import itView.springboot.vo.User;

@Mapper
public interface MyMapper {

    // 이미 있던 메서드
    User selectUserByNo(@Param("userNo") Long userNo);

    // 프로필 보기
    Attachment selectProfileImageByUser(@Param("userNo") Long userNo);

    // 이전 프로필 비활성화
    int disableOldProfileImages(@Param("userNo") Long userNo);

    // 새 프로필 저장
    int insertProfileImage(Attachment attm);
    
    int updateUserInfo(User user);

	int updateUserBasicAndSkin(User u);

	// MyMapper.java
	int existsUser(@Param("userNo") Long userNo);

	Long selectFirstUserNo();

	Long selectUserNoByUserId(@Param("userId") String userId);
    
	List<Product> searchProducts(@Param("keyword") String keyword);

    // 리뷰 저장
    int insertReview(Review review);
}
