package itView.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import itView.springboot.exception.ProductException;
import itView.springboot.service.ProductService;
import itView.springboot.vo.Answer;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Coupon;
import itView.springboot.vo.ExperienceApplication;
import itView.springboot.vo.ExperienceGroup;
import itView.springboot.vo.Order;
import itView.springboot.vo.Product;
import itView.springboot.vo.Question;
import itView.springboot.vo.Review;
import itView.springboot.vo.ReviewAnswer;
import itView.springboot.vo.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping({"/product","/seller"})
@RequiredArgsConstructor
public class ProductController {
	private final ProductService pService;
	private final BCryptPasswordEncoder bcrypt;
	
	
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
	public String productQnAPage(Model model, HttpSession session) {
		
		User loginUser = (User)session.getAttribute("loginUser");
		
		Product product = new Product();
		product.setUserNo(loginUser.getUserNo());
		
		ArrayList<Product> pList = pService.selectMyProduct(product);
		
		ArrayList<Question> qList = new ArrayList<Question>();
		
		int beforeAnswer = pService.selectBeforeAnswerCount();
		int afterAnswer = pService.selectAfterAnswerCount();
		
		for(int i = 0; i < pList.size(); i++) {
			int productNo = pList.get(i).getProductNo();
			qList.addAll(pService.selectQuestion(productNo));
			
		}
		
		model.addAttribute("qList", qList);
		model.addAttribute("ba", beforeAnswer);
		model.addAttribute("aa", afterAnswer);
		
		return "seller/productQnAPage";
	}
	
	// 상품 문의 답변 페이지 이동
	@GetMapping("answer/{questionNo}")
	public String answerPage(@PathVariable("questionNo") int questionNo, Model model) {
		
		Question question = pService.selectQuestionDetail(questionNo);
		Answer answer = pService.selectAnswerDetail(questionNo);
		
		model.addAttribute("answer", answer);
		model.addAttribute("question", question);
		
		return "seller/productAnswerPage";
	}
	
	// 내 상품 보기 페이지 이동
	@GetMapping("myProductPage")
	public String myProductPage(Model model, HttpSession session) {
		
		User loginUser = (User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		Product product = new Product();
		product.setUserNo(userNo);
		
		ArrayList<Product> rList = pService.countReview(userNo);
		
		ArrayList<ReviewAnswer> raCount = pService.countReviewAnswer(userNo);
		
		//System.out.println(raCount);
		
//		for(int i = 0; i < rList.size(); i++) {
			// UserNo 이용해서 DB에 있는 쿼리문 갖고와서 review_answer count해오기
			// rList에 있는 review_count랑 review_answer_count랑 연산
			// 그 결과를 화면에 고고
			// product에 있는 user_n
//		}
		
		model.addAttribute("rCount", rList);
		return "seller/myProductPage";
	}
	
	// 내 쿠폰 보기 페이지 이동
	@GetMapping("myCouponPage")
	public String myCouponPage(Model model, HttpSession session) {
		
		User loginUser = (User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		ArrayList<Coupon> list = pService.selectMyCoupon(userNo);
		
		model.addAttribute("list", list);
		return "seller/myCouponPage";
	}
	
	// 판매 내역 페이지 이동
	@GetMapping("mySellingPage")
	public String mySellingPage(Model model, HttpSession session) {
		
		User loginUser =(User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		ArrayList<Order> oList = pService.selectMyOrderList(userNo);
		
		for(int i = 0; i < oList.size(); i++) {
			if(oList.get(i).getDeliveryStatus().equals("before")) {
				oList.get(i).setDeliveryStatus("배송전");
			} else if(oList.get(i).getDeliveryStatus().equals("shipping")) {
				oList.get(i).setDeliveryStatus("배송중");
			} else if(oList.get(i).getDeliveryStatus().equals("after")) {
				oList.get(i).setDeliveryStatus("배송완료");
			}
		}
		
		model.addAttribute("oList", oList);
		
		return "seller/mySellingPage";
	}
	
	// 내 상품 상세 페이지 이동
	@GetMapping("/{id}")
	public String myProductDetailPage(@PathVariable("id") int productNo, HttpSession session, Model model) {
		
		User loginUser = (User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		Product product = new Product();
		product.setProductNo(productNo);
		product.setUserNo(userNo);
		
		Product p = pService.selectMyProductDetail(product);
		ArrayList<Coupon> cList = pService.selectMyCoupon(userNo);
		Attachment attm = pService.selectMyAttm(productNo);
		ArrayList<Review> rList = pService.selectReview(productNo);
		int reviewCount = pService.selectReviewCount(productNo);
		
		ArrayList<Question> question = pService.selectQuestion(productNo);
		
		//System.out.println(rList);
		
		
		ArrayList<Answer> answer = new ArrayList<Answer>();
		
		for(int i = 0; i < question.size(); i++) {
			int questionNo = question.get(i).getQuestionNo();
			answer.addAll( pService.selectAnswer(questionNo));
		}
		
		if(p.getIngredient().equals("natural")) {
			p.setIngredient("천연 & 식물 유래 성분");
		} else if(p.getIngredient().equals("vitamin")) {
			p.setIngredient("비타민 & 항산화 성분");
		} else if(p.getIngredient().equals("peptide")) {
			p.setIngredient("기능성 펩타이드 & 단백질 성분");
		} else if(p.getIngredient().equals("moisture")) {
			p.setIngredient("보습 & 피부장벽 특화 성분");
		} else if(p.getIngredient().equals("cleansing")) {
			p.setIngredient("피부 정화 & 각질 케어 성분");
		}
		
		if(p.getEcoFriendly().equals("Y")) {
			p.setEcoFriendly("친환경 제품");
		} else {
			p.setEcoFriendly("일반 제품");
		}
		
		model.addAttribute("p", p);
		model.addAttribute("cList", cList);
		model.addAttribute("attm", attm);
		model.addAttribute("rList", rList);
		model.addAttribute("reviewCount", reviewCount);
		model.addAttribute("qList", question);
		model.addAttribute("aList", answer);
		
		return "seller/myProductDetail";
	}
	
	// 상품 상세페이지 이동
	@GetMapping("/detail/{productNo}")
	public String productdetail(@PathVariable("productNo") int productNo, Model model) {
		
		Product product = new Product();
		product.setProductNo(productNo);
		Product p = pService.selectProductDetail(product);
		product.setUserNo(p.getUserNo());
		
		int userNo = product.getUserNo();
		
		ArrayList<Coupon> cList = pService.selectMyCoupon(userNo);
		Attachment attm = pService.selectMyAttm(productNo);
		ArrayList<Review> rList = pService.selectReview(productNo);
		int reviewCount = pService.selectReviewCount(productNo);
		ArrayList<Question> question = pService.selectQuestion(productNo);
		
		ArrayList<Answer> answer = new ArrayList<Answer>();
		
		for(int i = 0; i < question.size(); i++) {
			int questionNo = question.get(i).getQuestionNo();
			answer.addAll( pService.selectAnswer(questionNo));
		}
		
		if(p.getIngredient().equals("natural")) {
			p.setIngredient("천연 & 식물 유래 성분");
		} else if(p.getIngredient().equals("vitamin")) {
			p.setIngredient("비타민 & 항산화 성분");
		} else if(p.getIngredient().equals("peptide")) {
			p.setIngredient("기능성 펩타이드 & 단백질 성분");
		} else if(p.getIngredient().equals("moisture")) {
			p.setIngredient("보습 & 피부장벽 특화 성분");
		} else if(p.getIngredient().equals("cleansing")) {
			p.setIngredient("피부 정화 & 각질 케어 성분");
		}
		
		if(p.getEcoFriendly().equals("Y")) {
			p.setEcoFriendly("친환경 제품");
		} else {
			p.setEcoFriendly("일반 제품");
		}
		
		model.addAttribute("p", p);
		model.addAttribute("cList", cList);
		model.addAttribute("attm", attm);
		model.addAttribute("rList", rList);
		model.addAttribute("reviewCount", reviewCount);
		model.addAttribute("qList", question);
		model.addAttribute("aList", answer);
		
		return "Shopping/detail";
	}
	
	// 체험단 신청글 작성 페이지 이동
	@GetMapping("expWrite")
	public String expWritePage(Model model, HttpSession session) {
		User user = (User)session.getAttribute("loginUser");
		int userNo = user.getUserNo();
		
		model.addAttribute("userNo", userNo);
		return "seller/expWritePage";
	}
	
	// 체험단 관리 페이지 이동
	@GetMapping("experienceManagePage")
	public String experienceManagePage(Model model, HttpSession session) {
		
		User user = (User)session.getAttribute("loginUser");
		int userNo = user.getUserNo();
		
		ArrayList<ExperienceGroup> eList = pService.selectExpGroup(userNo);
		ArrayList<ExperienceApplication> eaList = new ArrayList<ExperienceApplication>();

		
		for(int i = 0; i < eList.size(); i++) {
			int expNo = eList.get(i).getExpNo();
			eaList.addAll(pService.selectExpApp(expNo));
		}
		
		System.out.println(eaList);
		
		model.addAttribute("eaList", eaList);
		
		return "seller/experienceManagePage";
	}
	
	// 상품 등록
	@PostMapping("insertProduct")
	public String insertProduct(@ModelAttribute Product product, @RequestParam("editordata") String productDetail, 
			@RequestParam("categoryParent") String categoryParent, @RequestParam("categoryName") String categoryName,
			@RequestParam("productImage") MultipartFile file, HttpSession session) {
		
		User loginUser = (User)session.getAttribute("loginUser");
		
		product.setProductDetail(productDetail);
		product.setUserNo(loginUser.getUserNo());
		product.setBrandName(loginUser.getBrandName());
		product.setProductCategory(categoryName);
		
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
		
		int result1 = pService.editProduct(product);
		int result2 = 1;
		Attachment attm = new Attachment();
		attm.setPositionNo(product.getProductNo());
		
		if(!file.getOriginalFilename().equals("")) {
			attm = pService.selectMyAttm(product.getProductNo());
			int deleteAttmResult = pService.deleteMyAttm(attm.getAttmNo());
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

	// 체험단 신청글 작성
	@PostMapping("/writeExp")
	public String writeExp(@ModelAttribute ExperienceGroup expGroup) {
		int result = pService.writeExp(expGroup);
		if(result > 0) {
			return "redirect:/seller/experienceManagePage";
		} else {
			throw new ProductException("체험단 신청글 작성을 실패하였습니다.");
		}
	}
	
	// 상품 문의 답변 입력
	@PostMapping("insertAnswer")
	public String insertAnswer(@ModelAttribute Answer answer) {
		int result = pService.insertAnswer(answer);
		if(result > 0) {
			return "redirect:/seller/productQnAPage";
		} else {
			throw new ProductException("상품 문의에 대한 답변 작성을 실패하였습니다.");
		}
	}
	
	// 상품 문의 답변 삭제
	@GetMapping("deleteAnswer/{answerNo}")
	public String deleteAnswer(@PathVariable("answerNo") int answerNo) {
		int result = pService.deleteAnswer(answerNo);
		if(result > 0) {
			return "redirect:/seller/productQnAPage";
		} else {
			throw new ProductException("상품 문의에 대한 답변 삭제를 실패하였습니다.");
		}
	}
	
	// 상품 리뷰 답변 삭제
	@GetMapping("/deleteReviewAnswer/{reviewAnswerId}/{productNo}")
	public String deleteReviewAnswer(@PathVariable("reviewAnswerId") int reviewAnswerId, @PathVariable("productNo") int productNo) {
		int result = pService.deleteReviewAnswer(reviewAnswerId);
		if(result > 0) {
			return "redirect:/product/" + productNo;
		} else {
			throw new ProductException("상품 리뷰에 대한 답변 삭제를 실패하였습니다.");
		}
	}
	
	// 체험단 신청 수락
	@GetMapping("/expApply/{applyNo}")
	public String updateExpApply(@PathVariable("applyNo") int applyNo) {
		int result = pService.updateExpApply(applyNo);
		if(result > 0) {
			return "redirect:/seller/experienceManagePage";
		} else {
			throw new ProductException("체험단 신청 수락을 실패하였습니다.");
		}
	}
	
	// 체험단 신청 거절
	@GetMapping("/expReject/{applyNo}")
	public String rejectExpApply(@PathVariable("applyNo") int applyNo) {
		int result = pService.rejectExpApply(applyNo);
		if(result > 0) {
			return "redirect:/seller/experienceManagePage";
		} else {
			throw new ProductException("체험단 신청 거절을 실패하였습니다.");
		}
	}

	// 쿠폰 다운로드
	@GetMapping("/downCoupon/{userNo}/{couponNo}/{productNo}")
	public String downCoupon(@PathVariable("userNo") int userNo, @PathVariable("couponNo") int couponNo, @PathVariable("productNo") int productNo) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("userNo", userNo);
		map.put("couponNo", couponNo);
		int result = pService.downCoupon(map);
		if(result > 0) {
			return "redirect:/product/detail/" + productNo;
		} else {
			throw new ProductException("쿠폰 다운로드가 실패하였습니다.");
		}
	}
}
