package itView.springboot.mapper;



import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import itView.springboot.vo.Attachment;
import itView.springboot.vo.Cart;
import itView.springboot.vo.CouponBox;
import itView.springboot.vo.Order;
import itView.springboot.vo.Wishlist;


@Mapper
public interface ShoppingMapper {

	int checkDelete(@Param("cNo")List<Integer> cNo);

	ArrayList<Cart> selectProduct(int uNo);

	int productDelete(int cNo);

	ArrayList<Attachment> selectThumbList(@Param("pNo") ArrayList<Integer> pN);

	ArrayList<CouponBox> selectCouponList(int uNo);

	ArrayList<Wishlist> selectWishList(int uNo);

	int wishcheckDelete(List<Integer> wNo);

	int wishproductDelete(int wNo);

	int insertWishToCart(@Param("userNo")int uNo, @Param("wishlistNo")List<Integer> wNo);

	int updateWishToCart(int uNo, List<Integer> wNo);

	ArrayList<Order> selectOrder(int oNo);

	Order selectOrderDetail(int oNo);

	ArrayList<Attachment> selectThumbListByOrderNo(int oNo);

	ArrayList<Integer> selectProductNoForOrder(int oNo);

	int orderToCart(int oNo);

	int purchaseConfirm(int oNo);

	int orderCancel(int oNo);



}
