package itView.springboot.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import itView.springboot.vo.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import itView.springboot.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductMapper mapper;


	public int insertProduct(Product product) {
		return mapper.insertProduct(product);
	}

	public ArrayList<Product> selectMyProduct(Product product) {
		return mapper.selectMyProduct(product);
	}

	public int editStock(Product product) {
		return mapper.editStock(product);
	}

	public int insertCoupon(Coupon coupon) {
		return mapper.insertCoupon(coupon);
	}

	public ArrayList<Coupon> selectMyCoupon(int userNo) {
		return mapper.selectMyCoupon(userNo);
	}

	public int editCoupon(Coupon coupon) {
		return mapper.editCoupon(coupon);
	}

	public int insertAttm(Attachment attm) {
		return mapper.insertAttm(attm);
	}

	public Attachment selectMyAttm(int productNo) {
		return mapper.selectMyAttm(productNo);
	}

	public int selectProductSeq() {
		return mapper.selectProductSeq();
	}

	public int editProduct(Product product) {
		return mapper.editProduct(product);
	}

	public int deleteMyAttm(int attmNo) {
		return mapper.deleteMyAttm(attmNo);
	}

	public int deleteProduct(int productNo) {
		return mapper.deleteProduct(productNo);
	}

	public int deleteAttm(int productNo) {
		return mapper.deleteAttm(productNo);
	}


    public List<Product> getLatestProducts() {
		return mapper.getLatestProducts();
    }

	public ArrayList<Review> selectReview(int productNo) {
		return mapper.selectReview(productNo);
	}

	public Product selectMyProductDetail(Product product) {
		return mapper.selectMyProductDetail(product);
	}

	public List<Product> getSearchProducts(PageInfo pi, String q) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.getSearchProducts(rowBounds, q);
	}

	public List<Product> getfilterProducts(String q, List<String> categories) {
		return mapper.getfilterProducts(q, categories);
	}

	public int getListCountWithSearch(String q) {
		return mapper.getListCountWithSearch(q);
	}

	// 게시글 수 가져오기
	public int getListCountWithFilter(String q, List<String> categories) {
		return mapper.getListCountWithFilter(q, categories);
	}

	// 페이징 적용해서 검색
	public List<Product> getFilterProducts(PageInfo pi, String q, List<String> categories) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.getFilterProducts(rowBounds, q, categories);
	}

	public int selectReviewCount(int productNo) {
		return mapper.selectReviewCount(productNo);
	}

	public int insertReviewAnswer(ReviewAnswer reviewAnswer) {
		return mapper.insertReviewAnswer(reviewAnswer);
	}

	public int writeExp(ExperienceGroup expGroup) {
		return mapper.writeExp(expGroup);
	}

	public Product selectProductDetail(Product product) {
		return mapper.selectProductDetail(product);
	}

	public ArrayList<Question> selectQuestion(int productNo) {
		return mapper.selectQuestion(productNo);
	}

	public ArrayList<Answer> selectAnswer(int questionNo) {
		return mapper.selectAnswer(questionNo);
	}

	public Answer selectAnswerDetail(int questionNo) {
		return mapper.selectAnswerDetail(questionNo);
	}

	public Question selectQuestionDetail(int questionNo) {
		return mapper.selectQuestionDetail(questionNo);
	}

	public int insertAnswer(Answer answer) {
		return mapper.insertAnswer(answer);
	}

	public int selectBeforeAnswerCount(int userNo) {
		return mapper.selectBeforeAnswerCount(userNo);
	}

	public int selectAfterAnswerCount(int userNo) {
		return mapper.selectAfterAnswerCount(userNo);
	}

	public int deleteAnswer(int answerNo) {
		return mapper.deleteAnswer(answerNo);
	}

	public int editAnswer(Answer answer) {
		return mapper.editAnswer(answer);
	}

	public ArrayList<Product> countReview(int userNo) {
		return mapper.countReview(userNo);
	}

	public ArrayList<ExperienceGroup> selectExpGroup(int userNo) {
		return mapper.selectExpGroup(userNo);
	}

	public ArrayList<ExperienceApplication> selectExpApp(int expNo) {
		return mapper.selectExpApp(expNo);
	}

	public ArrayList<Order> selectMyOrderList(int userNo) {
		return mapper.selectMyOrderList(userNo);
	}

	public ArrayList<ReviewAnswer> selectReviewAnswer(int productNo) {
		return mapper.selectReviewAnswer(productNo);
	}

	public int deleteReviewAnswer(int reviewAnswerId) {
		return mapper.deleteReviewAnswer(reviewAnswerId);
	}

	public int updateExpApply(int applyNo) {
		return mapper.updateExpApply(applyNo);
	}

	public int rejectExpApply(int applyNo) {
		return mapper.rejectExpApply(applyNo);
	}

	public int downCoupon(HashMap<String, Integer> map) {
		return mapper.downCoupon(map);
	}

	public int editReviewAnswer(ReviewAnswer reviewAnswer) {
		return mapper.editReviewAnswer(reviewAnswer);
	}

	public int deleteReview(Review review) {
		return mapper.deleteReview(review);
	}

	public int insertQuestion(Board board) {
		return mapper.insertQuestion(board);
	}

	public ArrayList<Board> selectMyBoard(int userNo) {
		return mapper.selectMyBoard(userNo);
	}

	public Board selectMyBoardDetail(int boardId) {
		return mapper.selectMyBoardDetail(boardId);
	}

	public ArrayList<AdminReply> selectBoardReply(int boardId) {
		return mapper.selectBoardReply(boardId);
	}

	public int updateQuestion(Board board) {
		return mapper.updateQuestion(board);
	}

	public int deleteQuestion(Board board) {
		return mapper.deleteQuestion(board);
	}

	public ArrayList<ExperienceGroup> selectMyExp(int userNo) {
		return mapper.selectMyExp(userNo);
	}


}
