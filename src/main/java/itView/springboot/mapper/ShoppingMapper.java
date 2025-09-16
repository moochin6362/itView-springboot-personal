package itView.springboot.mapper;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import itView.springboot.vo.Attachment;
import itView.springboot.vo.Cart;
import itView.springboot.vo.CouponBox;
import itView.springboot.vo.Order;
import itView.springboot.vo.OrderCancel;
import itView.springboot.vo.Product;
import itView.springboot.vo.Wishlist;


@Mapper
public interface ShoppingMapper {

	int checkDelete(@Param("cNo")List<Integer> cNo);

	ArrayList<Cart> selectProduct(int uNo);

	int productDelete(int cNo);

	ArrayList<Attachment> selectThumbList(@Param("pNo") ArrayList<Integer> pNo);

	ArrayList<CouponBox> selectCouponList(@Param("userNo") int uNo,@Param("userGrade")String userGrade);

	ArrayList<Wishlist> selectWishList(@Param("userNo") int uNo,@Param("wishSortType") String wishSortType);

	int wishcheckDelete(@Param("wNo") List<Integer> wNo);

	int wishproductDelete(int wNo);

	int insertWishToCart(@Param("userNo")int uNo, @Param("wNo")List<Integer> wNo);

	int updateWishToCart(@Param("userNo")int uNo, @Param("wNo")List<Integer> wNo);

	ArrayList<Order> selectOrder(int uNo);


	ArrayList<Attachment> selectThumbListByOrderNo(int oNo);

	ArrayList<Integer> selectProductNoForOrder(int uNo);

	 int checkCart(@Param("userNo") int uNo, @Param("productNo") int pNo);
	
	int orderToCart(@Param("userNo") int uNo,@Param("productNo") int pNo);

	int purchaseConfirm(@Param("orderNo") int oNo,@Param("userNo") int uNo);
	
	int orderCancel(int oNo);

	Map<String, Object> orderStatusCount(@Param("userNo") int uNo);

	ArrayList<Order> selectOrderDetail(@Param("orderNo") int oNo,@Param("userNo") int uNo);


	int insertCancel(OrderCancel cancel);

	int updateAutoConfirmOrder();

	int updateAutoUpdateDelivery();

	List<Cart> selectCartList(List<Integer> cNo);

	Product directPaySelectProduct(int pNo);

	int updateCartQty(@Param("productNo") int pNo, @Param("userNo") int uNo, @Param("amount") int amount);

	int insertCart(@Param("productNo") int pNo, @Param("userNo") int uNo, @Param("amount") int amount);

	int addWish(@Param("userNo") int uNo, @Param("productNo") int pNo);

	int removeWish(@Param("userNo") int uNo, @Param("productNo") int pNo);

	Integer checkWishNo(@Param("userNo") int uNo, @Param("productNo") int productNo);

	OrderCancel selectOrderCancel(int oNo);

	int updateCartamount(@Param("cartNo")int cNo, @Param("amount") int amount);

	CouponBox checkCouponbyCart(String coupon);

	int makeOrderNo();

	int insertOrder(Map<String, Object> insert);

	int deleteCartlist(List<Integer> cNo);

	int minusPoint(@Param("userNo") int uNo, @Param("usePoint")int usePoint);

	int addPoint(@Param("userNo") int uNo, @Param("savePoint")int savePoint);

	int updateCouponStatus(int couponNo);

	


	

	



}
