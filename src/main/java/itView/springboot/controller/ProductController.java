package itView.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import itView.springboot.exception.LoginException;
import itView.springboot.exception.ProductException;
import itView.springboot.exception.UserException;
import itView.springboot.service.ProductService;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Coupon;
import itView.springboot.vo.Product;
import itView.springboot.vo.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("loginUser")
@RequestMapping({"/product", "/login", "/seller"})
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService pService;
	private final BCryptPasswordEncoder bcrypt;
	

	@PostMapping("login")
	public String login(User u, Model model) {
		//System.out.println(bcrypt.encode("1234"));
		User loginUser = pService.login(u);
		
		if(loginUser != null && bcrypt.matches(u.getUserPassword(), loginUser.getUserPassword())) {
			model.addAttribute("loginUser", loginUser);
			if(loginUser.getUserType().equals("U") || loginUser.getUserType().equals("A")) {
				return "redirect:/";
			} else {
				return "redirect:/seller/home";
			}
		} else {
			throw new ProductException("로그인을 실패하였습니다.");
		}
		
	}
	
	@GetMapping("home")
	public String sellerPageHome() {
		return "seller/sellerPage";
	}
	
	// 상품 등록 페이지 이동
	@GetMapping("insertProductPage")
	public String insertProductPage() {
		return "seller/productInsertPage";
	}
	
	// 상품 수정 페이지 이동
	@GetMapping("/productEdit/{no}")
	public String editProductPage(Model model, HttpSession session, @PathVariable("no") int productNo) {
		
		User loginUser = (User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		Product product = new Product();
		product.setUserNo(userNo);
		product.setProductNo(productNo);
		
		ArrayList<Product> list = pService.selectMyProduct(product);
		
		//System.out.println(attm);
		
		model.addAttribute("list", list);
		return "seller/productEditPage";
	}
	
	// 재고 관리 페이지 이동
	@GetMapping("stockManagePage")
	public String stockManagePage(Model model, HttpSession session) {
		
		User loginUser = (User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		Product product = new Product();
		product.setUserNo(userNo);
		
		ArrayList<Product> list = pService.selectMyProduct(product);
		//System.out.println(list);
		model.addAttribute("list", list);
		return "seller/stockManagePage";
	}
	
	// 쿠폰 관리 페이지 이동
	@GetMapping("couponManagePage")
	public String couponeManagePage() {
		return "seller/couponManagePage";
	}
	
	// 상품 Q&A 페이지 이동
	@GetMapping("productQnAPage")
	public String productQnAPage() {
		return "seller/productQnAPage";
	}
	
	// 내 상품 보기 페이지 이동
	@GetMapping("myProductPage")
	public String myProductPage(Model model, HttpSession session) {
		
		User loginUser = (User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		Product product = new Product();
		product.setUserNo(userNo);
		
		ArrayList<Product> list = pService.selectMyProduct(product);
		
		//System.out.println(product);
		
		//System.out.println(list);
		
		model.addAttribute("list", list);
		return "seller/myProductPage";
	}
	
	// 내 쿠폰 보기 페이지 이동
	@GetMapping("myCouponPage")
	public String myCouponPage(Model model, HttpSession session) {
		
		User loginUser = (User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		ArrayList<Coupon> list = pService.selectMyCoupon(userNo);
		
		System.out.println(list);
		
		model.addAttribute("list", list);
		return "seller/myCouponPage";
	}
	
	// 판매 내역 페이지 이동
	@GetMapping("mySellingPage")
	public String mySellingPage() {
		return "seller/mySelling";
	}
	
	// 내 상품 상세 페이지 이동
	@GetMapping("/{id}")
	public String myProductDetailPage(@PathVariable("id") int productNo, HttpSession session, Model model) {
		
		User loginUser = (User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		Product product = new Product();
		product.setProductNo(productNo);
		product.setUserNo(userNo);
		
		ArrayList<Product> list = pService.selectMyProduct(product);
		ArrayList<Coupon> cList = pService.selectMyCoupon(userNo);
		Attachment attm = pService.selectMyAttm(productNo);
		
		model.addAttribute("list", list);
		model.addAttribute("cList", cList);
		model.addAttribute("attm", attm);
		
		return "seller/myProductDetail";
	}
	
	// 상품 등록
	@PostMapping("insertProduct")
	public String insertProduct(@ModelAttribute Product product, @RequestParam("editordata") String productDetail, 
			@RequestParam("categoryParent") String categoryParent, @RequestParam("categoryName") String categoryName,
			@RequestParam("productImage") MultipartFile file, HttpSession session) {
		
		User loginUser = (User)session.getAttribute("loginUser");
		
		//System.out.println(productDetail);
		
		product.setProductDetail(productDetail);
		product.setUserNo(loginUser.getUserNo());
		product.setBrandName(loginUser.getBrandName());
		product.setProductCategory(categoryName);
//		System.out.println(product);
//		System.out.println(file.getOriginalFilename());
//		System.out.println(categoryParent);
		
		
		// 상품 대표 이미지 저장
		Attachment attm = new Attachment();
		if(!file.getOriginalFilename().equals("")) {
			String[] returnArr = saveFile(file);
			if(returnArr[1] != null) {
				attm.setAttmName(file.getOriginalFilename());
				attm.setAttmRename(returnArr[1]);
				attm.setAttmPath(returnArr[0]);
			}
		}
		
		int result1 = pService.insertProduct(product);
		
		attm.setPositionNo(product.getProductNo());
		int result2 = pService.insertAttm(attm);
		if(result1 + result2 > 1) {
			return "redirect:/seller/myProductPage";
		} else {
			throw new ProductException("상품 등록을 실패하였습니다.");
		}
	}
	
	// 이미지 저장
	private String[] saveFile(MultipartFile file) {
		
		String savePath = "C:\\uploadFilesFinal\\product";
		
		File folder = new File(savePath);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		int ranNum = (int)(Math.random()*100000);
		String originFileName = file.getOriginalFilename();
		String renameFileName = sdf.format(new Date()) + ranNum + originFileName.substring(originFileName.lastIndexOf("."));
		
		String renamePath = folder + "\\" + renameFileName;
		try {
			file.transferTo(new File(renamePath));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		String[] returnArr = new String[2];
		returnArr[0] = savePath;
		returnArr[1] = renameFileName;
		
		return returnArr;
	}

	// 쿠폰 등록
	@PostMapping("insertCoupon")
	public String insertCoupon(@ModelAttribute Coupon coupon) {
		
		//System.out.println(coupon.getCouponDescription());
		//System.out.println(coupon.getCouponMinprice());
		//System.out.println(coupon.getCouponName());
		
		//System.out.println(coupon.getCouponEndDate());
		
		int result = pService.insertCoupon(coupon);
		if(result > 0) {
			return "redirect:/seller/myCouponPage";
		} else {
			throw new ProductException("쿠폰 등록을 실패하였습니다.");
		}
	}
	
	// 상품 수정
	@PostMapping("editProduct")
	public String editProduct(@ModelAttribute Product product, @RequestParam("productImage") MultipartFile file) {
		//System.out.println(product);
		
		int result1 = pService.editProduct(product);
		int result2 = 1;
		Attachment attm = new Attachment();
		attm.setPositionNo(product.getProductNo());
		
		//System.out.println(file);
		
		if(!file.getOriginalFilename().equals("")) {
			attm = pService.selectMyAttm(product.getProductNo());
			int deleteAttmResult = pService.deleteMyAttm(attm.getAttmNo());
			//System.out.println(attm);
			if(deleteAttmResult > 0) {
				deleteFile(attm.getAttmRename());
				if(!file.getOriginalFilename().equals("")) {
					String[] returnArr = saveFile(file);
					if(returnArr[1] != null) {
						attm.setAttmName(file.getOriginalFilename());
						attm.setAttmRename(returnArr[1]);
						attm.setAttmPath(returnArr[0]);
						result2 = pService.insertAttm(attm);
					}
				}
			}
		}
		
		if(result1 + result2 > 1) {
			return "redirect:/seller/myProductPage";
		} else {
			throw new ProductException("상품 수정을 실패하였습니다.");
		}
	}

	public void deleteFile(String attmRename) {
		String savePath = "C:\\uploadFilesFinal\\product";
		
		File f = new File(savePath + "\\" + attmRename);
		if(f.exists()) {
			f.delete();
		}
	}
	
	// 상품 삭제
	@GetMapping("productDelete/{no}")
	public String deleteProduct(@PathVariable("no") int productNo) {
		
		int result1 = pService.deleteProduct(productNo);
		int result2 = pService.deleteAttm(productNo);
		
		if(result1 + result2 > 1) {
			return "redirect:/seller/myProductPage";
		} else {
			throw new ProductException("상품 삭제를 실패하였습니다.");
		}
	}
}
