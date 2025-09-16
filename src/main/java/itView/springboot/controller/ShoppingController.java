package itView.springboot.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import itView.springboot.service.ShoppingService;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Cart;
import itView.springboot.vo.CouponBox;
import itView.springboot.vo.Order;
import itView.springboot.vo.OrderCancel;
import itView.springboot.vo.Product;
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
		int uNo = loginUser.getUserNo();
		
		ArrayList<Order>olist = sService.selectOrderDetail(oNo,uNo);
		ArrayList<Attachment>alist=sService.selectThumbListByOrderNo(uNo);
		OrderCancel cancelInfo =sService.selectOrderCancel(oNo);
		model.addAttribute("olist", olist);
		model.addAttribute("alist", alist);
		model.addAttribute("ci",cancelInfo);
		
		return "Shopping/cancelDetail";
	}

	@GetMapping("cancelReason")
	public String cancelReason(Model model, HttpSession session,@RequestParam("orderNo") int oNo) {
		
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}
		int uNo = loginUser.getUserNo();
		
		ArrayList<Order>olist = sService.selectOrderDetail(oNo,uNo);
		
		model.addAttribute("olist",olist);
		
		return "Shopping/cancelReason";
	}


	

	@GetMapping("cart")
	public String cart(Model model, HttpSession session) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}
		int uNo = loginUser.getUserNo();
		String userGrade=loginUser.getUserGrade();
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
		ArrayList<CouponBox> couponlist = sService.selectCouponList(uNo,userGrade);
		
		Map<Object, List<Cart>> cartGroup= clist.stream().collect(Collectors.groupingBy(c->c.getProductCompany()));
		model.addAttribute("clist", clist);
		model.addAttribute("alist", alist);
		model.addAttribute("couponlist", couponlist);
		model.addAttribute("cartGroup",cartGroup);
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
		int uNo = loginUser.getUserNo();
		ArrayList<Order>olist = sService.selectOrderDetail(oNo,uNo);
		ArrayList<Attachment>alist=sService.selectThumbListByOrderNo(uNo);
		
		model.addAttribute("olist", olist);
		model.addAttribute("alist", alist);
			
		return "Shopping/delivery";
	}

	
	@GetMapping("order")
	public String order(Model model,HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}
		
		
		int uNo = loginUser.getUserNo();
		
		ArrayList<Order>olist = sService.selectOrder(uNo);
		ArrayList<Attachment>alist=sService.selectThumbListByOrderNo(uNo);
		
		 Map<Integer, List<Order>> groupedOrder = olist.stream()
		            .collect(Collectors.groupingBy(Order::getOrderNo, LinkedHashMap::new, Collectors.toList()));

		Map<String, Object> count = sService.orderStatusCount(uNo);
		model.addAttribute("groupedOrder", groupedOrder.entrySet());
		model.addAttribute("count",count);
		model.addAttribute("alist",alist);
		
		return "Shopping/order";
	}

	
	@GetMapping("orderDetail")
	public String orderDetail(Model model,@RequestParam("orderNo") int oNo,HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}
		int uNo = loginUser.getUserNo();
		
		
		ArrayList<Order>olist = sService.selectOrderDetail(oNo,uNo);
		ArrayList<Attachment>alist=sService.selectThumbListByOrderNo(uNo);
		
		
		model.addAttribute("olist", olist);
		model.addAttribute("alist", alist);
		model.addAttribute("loginUser",loginUser);
		
		return "Shopping/orderDetail";
	}

	// ===========================================================
	// 찜
	@GetMapping({"WishList","wishSort"})
	public String WishList(Model model, HttpSession session,@RequestParam(value="wishSortType", defaultValue="newest")String wishsort) {

		User loginUser = (User) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "redirect:/login";
		}
		int uNo = loginUser.getUserNo();

		ArrayList<Wishlist> wlist = sService.selectWishList(uNo,wishsort);
		
		ArrayList<Integer> pNo = new ArrayList<>();
		for (Wishlist w : wlist) {
			if (!pNo.contains(w.getProductNo())) {
				pNo.add(w.getProductNo());
			}
		}

		ArrayList<Attachment> alist = new ArrayList<>();
		if (!pNo.isEmpty()) {
		    alist = sService.selectThumbList(pNo);
		}
		
		model.addAttribute("wlist", wlist);
		model.addAttribute("alist", alist);
		model.addAttribute("ws",wishsort);
		return "Shopping/WishList";
	} 

	// 찜에서 선택 삭제 눌럿을때
	@PostMapping("wishcheckDelete")
	@ResponseBody
	public int wishcheckDelete(@RequestParam("wishlistNo") List<Integer> wNo) {
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
	public int orderToCart(@RequestParam("productNo") int pNo,HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");

		int uNo = loginUser.getUserNo();
		int result = sService.orderToCart(uNo,pNo);
		
		return result;
	}
	
	@PostMapping("purchaseConfirm")
	@ResponseBody
	public int purchaseConfirm(@RequestParam("orderNo") int oNo,HttpSession session) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		int uNo = loginUser.getUserNo();
		int result = sService.purchaseConfirm(oNo,uNo);
		return result;
	}
	
	
	
	
	@PostMapping("orderCancel")
	@ResponseBody
	public int orderCancel(@RequestParam("orderNo") int oNo) {
		int result = sService.orderCancel(oNo);
		return result;
	}
	
	
	@PostMapping("insertCancel")
	@ResponseBody
	public int insertCancel(OrderCancel cancel, HttpSession session,@RequestParam("orderNo") int oNo) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		
		cancel.setUserNo(loginUser.getUserNo());
		cancel.setOrderNo(oNo);
		return sService.insertCancel(cancel);
	}
	
	
	@GetMapping("payMent")
	public String selectProductList(@RequestParam(value = "cartNo", required = false)List<Integer> cNo,HttpSession session,Model model,
								@RequestParam(value = "coupon", required = false) String coupon,
								@RequestParam(value = "productNo", required = false)Integer pNo, @RequestParam(value = "amount", required = false)Integer amount) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		int uNo = loginUser.getUserNo();
	
		List<Cart> clist=new ArrayList<>();
		int totalPrice=0;
		CouponBox selectedCoupon = null;
	    int discountAmount = 0;
	    
	    if(cNo !=null) {
	    	clist =sService.selectCartList(cNo);
	    	for(Cart c:clist) {
	    		totalPrice+=c.getProductPrice() * c.getAmount();
	    	}
	    	model.addAttribute("orderType",2);
	    	
	    	if(coupon != null && !coupon.isBlank()) {
	    		selectedCoupon=sService.checkCouponbyCart(coupon);
	    		if(selectedCoupon != null) {
	    			int discountRate=selectedCoupon.getCouponDiscount();
	    			discountAmount = (int) Math.floor(totalPrice * (discountRate / 100.0));
	    		}
	    	}
	    	
	    	
	    }else if(pNo !=null && amount != null) {
	    	Product p = sService.directPaySelectProduct(pNo);
	    	Cart c = new Cart();
	    	c.setProductNo(p.getProductNo());
	    	c.setProductName(p.getProductName());
	    	c.setProductCompany(p.getBrandName());
	    	c.setProductPrice(p.getProductPrice());
	    	c.setAmount(amount);
	    	
	    	clist.add(c);
	    	totalPrice=p.getProductPrice()*amount;
	    	model.addAttribute("orderType",1);
	    }
	    
	    

		model.addAttribute("clist",clist);
		model.addAttribute("totalPrice",totalPrice);
		model.addAttribute("coupon",selectedCoupon);
		model.addAttribute("loginUser",loginUser);
		model.addAttribute("discountAmount",discountAmount);
		model.addAttribute("cartNoList", cNo);

		
		
		
		
		return  "Shopping/payMent";
	}
	
	private Map<String, Object> confirmPayment(String paymentKey, String orderId, int amount) {
	    String tossSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
	    String url = "https://api.tosspayments.com/v1/payments/confirm";

	   
	    Map<String, Object> body = new HashMap<>();
	    body.put("paymentKey", paymentKey);
	    body.put("orderId", orderId);
	    body.put("amount", amount);

	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    String encodedAuth = Base64.getEncoder().encodeToString((tossSecretKey + ":").getBytes());
	    headers.set("Authorization", "Basic " + encodedAuth);

	    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
	    RestTemplate restTemplate = new RestTemplate();

	    ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

	    return response.getBody(); 
	}
	
	
	
	@GetMapping("paymentSuccess")
	public String paymentSuccess(@RequestParam Map<String, String> map,
	                             @RequestParam("orderType") int orderType,
	                             @RequestParam(value = "cartNo", required = false) List<Integer> cNo,
	                             @RequestParam(value = "productNo", required = false) Integer pNo,
	                             @RequestParam(value = "amount", required = false) Integer amount,
	                             @RequestParam("finalPrice") int finalPrice,
	                             HttpSession session,
	                             Model model) {

	    User loginUser = (User) session.getAttribute("loginUser");
	    int uNo = loginUser.getUserNo();

	    int oNo = sService.makeOrderNo();
	    long randomNo = System.currentTimeMillis() + new Random().nextInt(1000);
	    
	    
	    String paymentKey = map.get("paymentKey");
	    String orderId = map.get("orderId");
	    int payAmount = Integer.parseInt(map.get("amount"));

	    Map<String, Object> tossResult = confirmPayment(paymentKey, orderId, payAmount);
	    String method = (String) tossResult.get("method");
	    
	    
	    Map<String,Object> both = new HashMap<>();
	    both.put("orderNo", oNo);
	    both.put("userNo", uNo);
	    both.put("orderType", orderType);

	    
	    both.put("paymentMethod", method);   
	    both.put("paymentKey", map.get("paymentKey"));  
	    both.put("orderId", map.get("orderId"));

	    
	    both.put("personalCouponNo", map.get("couponNo"));
	    both.put("usePoint", map.get("usePoint"));
	    both.put("savePoint", map.get("savePoint"));
	    both.put("discountAmount", map.get("discountAmount"));
	    both.put("deliveryFee", map.get("deliveryFee"));

	    both.put("userName", map.get("shipName"));
	    both.put("userPhone", map.get("shipPhone"));
	    both.put("userAddress", map.get("shipAddr") + " " + map.get("shipAddrplus"));
	    both.put("payPrice", finalPrice);
	    both.put("deliveryNo", randomNo);
	    both.put("deliveryCompany", "cj대한통운");

	    
	    if (orderType == 2 && cNo != null) {
	        List<Cart> clist = sService.selectCartList(cNo);
	        for (Cart c : clist) {
	            Map<String,Object> insert = new HashMap<>(both);
	            insert.put("orderTargetNo", c.getProductNo());
	            insert.put("orderCount", c.getAmount());
	            sService.insertOrder(insert);
	        }
	        sService.deleteCartlist(cNo);

	    
	    } else if (orderType == 1 && pNo != null) {
	        Product p = sService.directPaySelectProduct(pNo);
	        Map<String,Object> insert = new HashMap<>(both);
	        insert.put("orderTargetNo", pNo);
	        insert.put("orderCount", amount);
	        sService.insertOrder(insert);
	    }

	    if (map.get("usePoint") != null) {  
	
	        int usePoint = Integer.parseInt(map.get("usePoint"));
	        int result=sService.minusPoint(uNo, usePoint);
	    }
	    if (map.get("savePoint") != null) {  
	        int savePoint = Integer.parseInt(map.get("savePoint"));
	        int result=sService.addPoint(uNo, savePoint);
	    }

	    if (map.get("couponNo") != null) {  
	        int couponNo = Integer.parseInt(map.get("couponNo"));
	        int result=sService.updateCouponStatus(couponNo);
	    }
	    
	    
	    model.addAttribute("finalPrice", finalPrice);
	    model.addAttribute("payInfo", map);

	    return "shopping/paymentSuccess";
	}

	
	
	
	
	
	
	@GetMapping("paymentFail")
	public String paymentFail(@RequestParam Map<String, String> map, Model model) {
		model.addAttribute("payInfo", map);
		return "Shopping/paymentFail";
	}
	
	
	
	
	@PostMapping("productToCart")
	@ResponseBody
	public int productToCart(@RequestParam("productNo")int pNo, HttpSession session,@RequestParam("amount")int amount) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		int uNo = loginUser.getUserNo();
		
		int result=sService.productToCart(pNo,uNo,amount);
		
		return result;
	}

	@GetMapping("detail")
	public String detail() {
		
		
		
		return"Shopping/detail";
	}
	
	@PostMapping("addWish")
	@ResponseBody
	public int addWish(@RequestParam("productNo")int pNo,HttpSession session, Model model) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		int uNo = loginUser.getUserNo();
		
		int result=sService.addWish(uNo,pNo);
		
		return result;
	}
	
	@PostMapping("removeWish")
	@ResponseBody
	public int removeWish(@RequestParam("productNo")int pNo,HttpSession session, Model model) {
		
		User loginUser = (User) session.getAttribute("loginUser");
		int uNo = loginUser.getUserNo();
		
		int result=sService.removeWish(uNo,pNo);
		
		return result;
	}
	
	
	@PostMapping("updateCartamount")
	@ResponseBody
	public int updateCartamount(@RequestParam("cartNo") int cNo,@RequestParam("amount")int amount) {
		int result=sService.updateCartamount(cNo,amount);
		return result;
	}
}
