package itView.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import itView.springboot.mapper.ShoppingMapper;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Cart;
import itView.springboot.vo.CouponBox;
import itView.springboot.vo.Order;
import itView.springboot.vo.OrderCancel;
import itView.springboot.vo.Product;
import itView.springboot.vo.Wishlist;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingService {
	
	private final ShoppingMapper mapper;
	
	public int checkDelete(List<Integer> cNo) {
		// TODO Auto-generated method stub
		return mapper.checkDelete(cNo);
	}

	public ArrayList<Cart> selectProduct(int uNo) {
		// TODO Auto-generated method stub
		return mapper.selectProduct(uNo);
	}

	public int productDelete(int cNo) {
		// TODO Auto-generated method stub
		return mapper.productDelete(cNo);
	}

	public ArrayList<Attachment> selectThumbList(ArrayList<Integer> pNo ) {
		// TODO Auto-generated method stub
		return mapper.selectThumbList(pNo);
		
	}

	public ArrayList<Order> selectOrder(int uNo) {
		// TODO Auto-generated method stub
		return mapper.selectOrder(uNo);
	}
	
	
	public ArrayList<Attachment> selectThumbListByOrderNo(int uNo) {
		 ArrayList<Integer> pNo = mapper.selectProductNoForOrder(uNo); 
		    if (pNo == null || pNo.isEmpty()) return new ArrayList<>();            
		    return mapper.selectThumbList(pNo); 
	}
	public ArrayList<CouponBox> selectCouponList(int uNo, String userGrade) {
		// TODO Auto-generated method stub
		return mapper.selectCouponList(uNo,userGrade);
	}

	public ArrayList<Wishlist> selectWishList(int uNo, String wishsort) {
		// TODO Auto-generated method stub
		return mapper.selectWishList(uNo,wishsort);
	}

	public int wishcheckDelete(List<Integer> wNo) {
		// TODO Auto-generated method stub
		return mapper.wishcheckDelete(wNo);
	}

	public int wishproductDelete(int wNo) {
		// TODO Auto-generated method stub
		return mapper.wishproductDelete(wNo);
	}

	public int insertWishToCart(int uNo, List<Integer> wNo) {
		// TODO Auto-generated method stub
		return mapper.insertWishToCart(uNo,wNo);
	}

	public int updateWishToCart(int uNo, List<Integer> wNo) {
		// TODO Auto-generated method stub
		return mapper.updateWishToCart(uNo,wNo);
	}

	public int orderToCart(int uNo,int pNo) {
		 int exist = mapper.checkCart(uNo, pNo);
		 if (exist > 0) {
	            return -1;
	        }
		return mapper.orderToCart(uNo,pNo);
	}

	public int purchaseConfirm(int oNo,int uNo) {
		// TODO Auto-generated method stub
		
		return mapper.purchaseConfirm(oNo,uNo);
	}

	public int orderCancel(int oNo) {
		// TODO Auto-generated method stub
		return mapper.orderCancel(oNo);
	}

	public Map<String, Object> orderStatusCount(int uNo) {
		// TODO Auto-generated method stub
		return mapper.orderStatusCount(uNo);
		
		
	}

	public ArrayList<Order> selectOrderDetail(int oNo,int uNo) {
		// TODO Auto-generated method stub
		return mapper.selectOrderDetail(oNo,uNo);
	}

	public int insertCancel(OrderCancel cancel) {
		// TODO Auto-generated method stub
		return mapper.insertCancel(cancel);
	}

	public int updateAutoConfirmOrder() {
		// TODO Auto-generated method stub
		return mapper.updateAutoConfirmOrder();
	}

	public int updateAutoUpdateDelivery() {
		// TODO Auto-generated method stub
		return mapper.updateAutoUpdateDelivery();
	}

	
	public List<Cart> selectCartList(List<Integer> cNo) {
		// TODO Auto-generated method stub
		return mapper.selectCartList(cNo);
	}

	public Product directPaySelectProduct(int pNo) {
		// TODO Auto-generated method stub
		return mapper.directPaySelectProduct(pNo);
	}


	public int productToCart(int pNo, int uNo, int amount) {
		int exist = mapper.checkCart(uNo, pNo);
		 if (exist > 0) {
	            return mapper.updateCartQty(pNo, uNo, amount);
	        }else {
	        	return mapper.insertCart(pNo, uNo, amount);
	        }
		
	}

	public Integer checkWishNo(int uNo, int productNo) {
		// TODO Auto-generated method stub
		return mapper.checkWishNo(uNo, productNo);
	}

	public int addWish(int uNo, int pNo) {
		Integer exist = mapper.checkWishNo(uNo, pNo);
		if (exist != null && exist >0) {
			return exist;
		}
		int result=mapper.addWish(uNo,pNo);
		if(result>0) {
			
			return mapper.checkWishNo(uNo, pNo);
		}
		return 0;
	}

	public int removeWish(int uNo, int pNo) {
		int result = mapper.removeWish(uNo, pNo);
		if (result > 0) {
			return 1;
		}
		return -1;
	}

	public OrderCancel selectOrderCancel(int oNo) {
		// TODO Auto-generated method stub
		return mapper.selectOrderCancel(oNo);
	}

	public int updateCartamount(int cNo, int amount) {
		// TODO Auto-generated method stub
		return mapper.updateCartamount(cNo,amount);
	}

	public CouponBox checkCouponbyCart(String coupon) {
		// TODO Auto-generated method stub
		return mapper.checkCouponbyCart(coupon);
	}

	
	public int makeOrderNo() {
		// TODO Auto-generated method stub
		return mapper.makeOrderNo();
	}

	public int insertOrder(Map<String, Object> insert) {
		// TODO Auto-generated method stub
		return mapper.insertOrder(insert);
	}

	public int deleteCartlist(List<Integer> cNo) {
		// TODO Auto-generated method stub
		return mapper.deleteCartlist(cNo);
	}

	public int minusPoint(int uNo, int usePoint) {
		// TODO Auto-generated method stub
		return mapper.minusPoint(uNo,usePoint);
	}

	public int addPoint(int uNo, int savePoint) {
		// TODO Auto-generated method stub
		return mapper.addPoint(uNo,savePoint);
	}

	public int updateCouponStatus(int couponNo) {
		// TODO Auto-generated method stub
		return mapper.updateCouponStatus(couponNo);
	}

	

	


	

	
	

	

	
	

	
	
	

	

	

}
