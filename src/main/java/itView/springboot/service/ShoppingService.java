package itView.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import itView.springboot.mapper.ShoppingMapper;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Cart;
import itView.springboot.vo.CouponBox;
import itView.springboot.vo.Order;
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

	public ArrayList<Order> selectOrder(int oNo) {
		// TODO Auto-generated method stub
		return mapper.selectOrder(oNo);
	}
	
	
	public ArrayList<Attachment> selectThumbListByOrderNo(int oNo) {
		 ArrayList<Integer> pNo = mapper.selectProductNoForOrder(oNo); 
		    if (pNo == null || pNo.isEmpty()) return new ArrayList<>();            
		    return mapper.selectThumbList(pNo); 
	}
	public ArrayList<CouponBox> selectCouponList(int uNo) {
		// TODO Auto-generated method stub
		return mapper.selectCouponList(uNo);
	}

	public ArrayList<Wishlist> selectWishList(int uNo) {
		// TODO Auto-generated method stub
		return mapper.selectWishList(uNo);
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

	public int orderToCart(int oNo) {
		// TODO Auto-generated method stub
		return mapper.orderToCart(oNo);
	}

	public int purchaseConfirm(int oNo) {
		// TODO Auto-generated method stub
		return mapper.purchaseConfirm(oNo);
	}

	public int orderCancel(int oNo) {
		// TODO Auto-generated method stub
		return mapper.orderCancel(oNo);
	}

	

	

	

}
