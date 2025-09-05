package itView.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import itView.springboot.service.ShoppingService;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Cart;
import itView.springboot.vo.CouponBox;
import itView.springboot.vo.Order;
import itView.springboot.vo.User;
import itView.springboot.vo.Wishlist;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/Shopping")
@RequiredArgsConstructor
@SessionAttributes("loginUser")
public class ShoppingController {

	private final ShoppingService sService;

	@GetMapping("cancelDetail")
	public String cancelDetail(Model model, HttpSession session,@RequestParam("orderNo") int oNo) {
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}
		ArrayList<Order>olist = sService.selectOrder(oNo);
		ArrayList<Attachment>alist=sService.selectThumbListByOrderNo(oNo);
		
		model.addAttribute("olist", olist);
		model.addAttribute("alist", alist);
		
		
		return "Shopping/cancelDetail";
	}

	@GetMapping("cancelReason")
	public String cancelReason() {
		
		
		return "Shopping/cancelReason";
	}

	@GetMapping("cart")
	public String cart(Model model, HttpSession session) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}
		int uNo = loginUser.getUserNo();
		ArrayList<Cart> clist = sService.selectProduct(uNo);
 
		ArrayList<Integer> pNo = new ArrayList<>();
		for (Cart c : clist) {
			if (!pNo.contains(c.getProductNo())) {
				pNo.add(c.getProductNo());
			}
		}

		ArrayList<Attachment> alist = new ArrayList<>();
		if (!pNo.isEmpty()) {
		    alist = sService.selectThumbList(pNo);
		}
		ArrayList<CouponBox> couponlist = sService.selectCouponList(uNo);
		model.addAttribute("clist", clist);
		model.addAttribute("alist", alist);
		model.addAttribute("couponlist", couponlist);
		return "Shopping/cart";
	}

	// 장바구니에서 선택삭제 버튼 눌럿을때
	@PostMapping("checkDelete")
	@ResponseBody
	public int checkdelete(@RequestParam("cartNo") List<Integer> cNo) {
		int result = sService.checkDelete(cNo);

		return result;
	}

	// 장바구니에서 상품 개별 삭제할때
	@PostMapping("productDelete")
	@ResponseBody
	public int productDelete(@RequestParam("cartNo") int cNo) {
		int result = sService.productDelete(cNo);
		return result;
	}

	@GetMapping("delivery")
	public String delivery(Model model, HttpSession session,@RequestParam("orderNo") int oNo) {
		
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}
		ArrayList<Order>olist = sService.selectOrder(oNo);
		ArrayList<Attachment>alist=sService.selectThumbListByOrderNo(oNo);
		
		model.addAttribute("olist", olist);
		model.addAttribute("alist", alist);
			
		return "Shopping/delivery";
	}

	
	@GetMapping("order")
	public String order(Model model, @RequestParam("orderNo")int oNo,HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}
		ArrayList<Order>olist = sService.selectOrder(oNo);
		ArrayList<Attachment>alist=sService.selectThumbListByOrderNo(oNo);
		
		model.addAttribute("olist", olist);
		model.addAttribute("alist", alist);
		
		
		return "Shopping/order";
	}

	
	@GetMapping("orderDetail")
	public String orderDetail(Model model,@RequestParam("orderNo") int oNo,HttpSession session,@RequestParam("orderTargetNo") int otargetNo) {
		
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}
		ArrayList<Order>olist = sService.selectOrder(oNo);
		ArrayList<Attachment>alist=sService.selectThumbListByOrderNo(oNo);
		
		model.addAttribute("olist", olist);
		model.addAttribute("alist", alist);
		
		return "Shopping/orderDetail";
	}

	// ===========================================================
	// 찜
	@GetMapping({"WishList","wishSort","wishToCart"})
	public String WishList(Model model, HttpSession session,@RequestParam(value="wishSortType", defaultValue="latest")String csSortType) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}
		int uNo = loginUser.getUserNo();

		ArrayList<Wishlist> wlist = sService.selectWishList(uNo);
		
		ArrayList<Integer> pNo = new ArrayList<>();
		for (Wishlist w : wlist) {
			if (!pNo.contains(w.getProductNo())) {
				pNo.add(w.getProductNo());
			}
		}

		ArrayList<Attachment> alist = sService.selectThumbList(pNo);
		
		
		model.addAttribute("wlist", wlist);
		model.addAttribute("alist", alist);

		return "Shopping/WishList";
	} 

	// 찜에서 선택 삭제 눌럿을때
	@PostMapping("wishcheckdelete")
	@ResponseBody
	public int wishcheckdelete(@RequestParam("wishlistNo") List<Integer> wNo) {
		int result = sService.wishcheckDelete(wNo);

		return result;
	}

	// 찜에서 상품 개별 삭제할때
	@PostMapping("wishproductDelete")
	@ResponseBody
	public int wishproductDelete(@RequestParam("wishlistNo") int wNo) {
		int result = sService.wishproductDelete(wNo);
		return result;
	}
	
	//찜에서장바구니로
	@PostMapping("wishToCart")
	@ResponseBody
	public int wishToCart(@RequestParam("wishlistNo") List<Integer> wNo,HttpSession session) {
		User loginUser = (User) session.getAttribute("loginUser");

		if(loginUser == null) return -1;
		
		int uNo = loginUser.getUserNo();
		
		int updated = sService.updateWishToCart(uNo,wNo);
		int inserted =sService.insertWishToCart(uNo, wNo);
		
		return updated+inserted;
		
	}
	@PostMapping("orderToCart")
	@ResponseBody
	public int orderToCart(@RequestParam("orderNo") int oNo) {
		int result = sService.orderToCart(oNo);
		return result;
	}
	
	@PostMapping("purchaseConfirm")
	@ResponseBody
	public int purchaseConfirm(@RequestParam("orderNo") int oNo) {
		int result = sService.purchaseConfirm(oNo);
		return result;
	}
	
	@PostMapping("orderCancel")
	@ResponseBody
	public int orderCancel(@RequestParam("orderNo") int oNo) {
		int result = sService.orderCancel(oNo);
		return result;
	}

}
