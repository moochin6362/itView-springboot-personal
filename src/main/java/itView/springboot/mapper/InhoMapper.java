package itView.springboot.mapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import itView.springboot.vo.Attachment;
import itView.springboot.vo.Board;
import itView.springboot.vo.Coupon;
import itView.springboot.vo.Point;
import itView.springboot.vo.Product;
import itView.springboot.vo.Report;
import itView.springboot.vo.User;

@Mapper
public interface InhoMapper {
	
	 int insertUser(User u);

	 User login(User u);

	 int checkId(String userId);

	 int enrollNotice(Board b);

	 int enrollCoupon(Coupon c);

	 List<User> selectTargetUser(String couponTarget);

	 int insertCouponBox(Map<String, Object> map);

	 int getCouponCount(HashMap<String, String> map);

	 ArrayList<Coupon> selectCouponList(HashMap<String, String> map, RowBounds rowBounds);

	 int getCouponBoardCount(HashMap<String, String> map);

	 ArrayList<Board> selectCouponBoardList(HashMap<String, String> map, RowBounds rowBounds);
	 
	 public void insertAttachment(Attachment attm);

	 Coupon selectCoupon(int cId);

	 Board selectCouponBoard(int bId);

	 int updateCoupon(Coupon c);

	 int updateCouponBoard(Board b);

	 int enrollPoint(Point p);

	 int updatePoint(Point p);

	 int getPointCount(HashMap<String, String> map);

	 ArrayList<Point> selectPointList(HashMap<String, String> map, RowBounds rowBounds);

	 int getPointByName(String pointName);

	 int addPoint(Map<String, Object> map);

	 int getRankingCount(HashMap<String, String> map);

	 ArrayList<Product> selectRankingList(HashMap<String, String> map);

	 Point selectPoint(int pNo);

	 int getNoticeCount(HashMap<String, String> map);

	 ArrayList<Board> selectNoticeList(HashMap<String, String> map, RowBounds rowBounds);

	 int deleteNotice(int bId);

	 int deleteCoupon(int cNo);

	 int deletePoint(int pNo);

	 int updateNotice(Board b);

	 Board selectNotice(int bId);

	 String selectThumbnail(int productNo);

	 Product selectProduct(int pNo);

	 int getReportProductCount(HashMap<String, String> map);

	 ArrayList<Product> selectReportProductList(HashMap<String, String> map, RowBounds rowBounds);

	 Product selectReportProduct(int pNo);

	 int getReportCount(int pNo);

	 ArrayList<Report> selectReportList(int pNo, RowBounds rowBounds);

	 int updateReportModifyDate(Map<String, Object> map);
	 
	 int updateProductState(int productNo);

	 void activateProducts(LocalDate today); // @Param("today")

	 int enrollReport(Report r);


}
