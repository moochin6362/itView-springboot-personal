package itView.springboot.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import itView.springboot.vo.AdminReply;
import itView.springboot.vo.Answer;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Board;
import itView.springboot.vo.Coupon;
import itView.springboot.vo.ExperienceApplication;
import itView.springboot.vo.ExperienceGroup;
import itView.springboot.vo.Order;
import itView.springboot.vo.Product;
import itView.springboot.vo.Question;
import itView.springboot.vo.Reply;
import itView.springboot.vo.Review;
import itView.springboot.vo.ReviewAnswer;
import itView.springboot.vo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface ProductMapper {

	int insertProduct(Product product);

	ArrayList<Product> selectMyProduct(Product product);

	int editStock(Product product);

	int insertCoupon(Coupon coupon);

	ArrayList<Coupon> selectMyCoupon(int userNo);

	int editCoupon(Coupon coupon);

	int insertAttm(Attachment attm);

	Attachment selectMyAttm(int productNo);

	int selectProductSeq();

	int editProduct(Product product);

	int deleteMyAttm(int attmNo);

	int deleteProduct(int productNo);

	int deleteAttm(int productNo);

	List<Product> getLatestProducts();

	List<Product> getSearchProducts(@Param("rowBounds") RowBounds rowBounds, String q);

	List<Product> getfilterProducts(String q, List<String> categories);

	int getListCountWithSearch(String q);

	// Mapper.java
	List<Product> getFilterProducts(@Param("rowBounds") RowBounds rowBounds,
									@Param("q") String q,
									@Param("categories") List<String> categories);

	int getListCountWithFilter(@Param("q") String q,
							   @Param("categories") List<String> categories);

	Product selectMyProductDetail(Product product);

	ArrayList<Review> selectReview(int productNo);

	int selectReviewCount(int productNo);

	int insertReviewAnswer(ReviewAnswer reviewAnswer);

	int writeExp(ExperienceGroup expGroup);

	Product selectProductDetail(Product product);

	ArrayList<Question> selectQuestion(int productNo);

	ArrayList<Answer> selectAnswer(int questionNo);

	Answer selectAnswerDetail(int questionNo);

	Question selectQuestionDetail(int questionNo);

	int insertAnswer(Answer answer);

	int selectBeforeAnswerCount(int userNo);

	int selectAfterAnswerCount(int userNo);

	int deleteAnswer(int answerNo);

	int editAnswer(Answer answer);

	ArrayList<Product> countReview(int userNo);

	ArrayList<ExperienceGroup> selectExpGroup(int userNo);

	ArrayList<ExperienceApplication> selectExpApp(int expNo);

	ArrayList<Order> selectMyOrderList(int userNo);

	ArrayList<ReviewAnswer> selectReviewAnswer(int productNo);

	int deleteReviewAnswer(int reviewAnswerId);

	int updateExpApply(int applyNo);

	int rejectExpApply(int applyNo);

	int downCoupon(HashMap<String, Integer> map);

	int editReviewAnswer(ReviewAnswer reviewAnswer);

	int deleteReview(Review review);

	int insertQuestion(Board board);

	ArrayList<Board> selectMyBoard(int userNo);

	Board selectMyBoardDetail(int boardId);

	ArrayList<AdminReply> selectBoardReply(int boardId);

	int updateQuestion(Board board);

	int deleteQuestion(Board board);

	ArrayList<ExperienceGroup> selectMyExp(int userNo);


}

