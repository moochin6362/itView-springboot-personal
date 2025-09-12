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

	
	
	

	

	

}
