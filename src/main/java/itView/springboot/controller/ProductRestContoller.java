package itView.springboot.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import itView.springboot.service.ProductService;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Coupon;
import itView.springboot.vo.Product;
import itView.springboot.vo.Review;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping({"/product", "/seller"})
@RequiredArgsConstructor
public class ProductRestContoller {
	
	private final WebApplicationContext context;
	private final ProductService pService;
	
	@PostMapping("imageUpload")
	public String imageUpload(@RequestParam("file") ArrayList<MultipartFile> file) {
		JSONObject json = new JSONObject();
		
		for(int i = 0; i < file.size(); i++) {
			MultipartFile upload = file.get(i);
			if(!upload.getOriginalFilename().equals("")) {
				String[] returnArr = saveFile(upload);
				if(returnArr[1] != null) {
					Attachment a = new Attachment();
					a.setAttmName(upload.getOriginalFilename());
					a.setAttmRename(returnArr[1]);
					a.setAttmPath(returnArr[0]);
					
					json.put("url", returnArr[1]);
					json.put("responseCode", "success");
				}
			}
		}
		
		return json.toString();
	}
	
	public String[] saveFile(MultipartFile upload) {
		
		// 파일 저장소 지정
		String savePath = "C:\\uploadFilesFinal\\product";
		
		// 폴더 생성
		File folder = new File(savePath);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		// file이름 rename 과정
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		int ranNum = (int)(Math.random()*100000);
		String originFileName = upload.getOriginalFilename();
		String renameFileName = sdf.format(new Date()) + ranNum + originFileName.substring(originFileName.lastIndexOf("."));
		
		// file 저장
		String renamePath = folder + "\\" + renameFileName;
		try {
			upload.transferTo(new File(renamePath));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		// String[]에 담아 리턴
		String[] returnArr = new String[2];
		returnArr[0] = savePath;
		returnArr[1] = renameFileName;
		
		return returnArr;
		
	}
	
	@PostMapping("imageDelete")
	public String imageDelete(@RequestParam("imageUrl") String imageUrl) {
		
		//System.out.println(imageUrl);
		
		// 이미지 URL 에서 파일 이름만 가져오기
		String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		
		// 파일 경로
		String savePath = "C:\\Users\\user\\Desktop\\itView-springboot\\src\\main\\resources\\static\\image\\upload";
		
		File file = new File(savePath + "\\" + fileName);
		
		if(file.exists()) {
			if(file.delete()) {
				return "파일 삭제 성공";
			} else {
				return "파일 삭제 실패";
			}
		} else {
			return "파일이 존재하지 않음";
		}
	}
	
	// 상품 재고량 수정
	@PutMapping("Stock")
	public int editStock(@RequestBody Product product) {
		//System.out.println(product);
		return pService.editStock(product);
	}
	
	// 상품 할인율 수정
	@PutMapping("Coupon")
	public int editCoupon(@RequestBody Coupon coupon) {
		return pService.editCoupon(coupon);
	}
	
	// 상품 리뷰 조회
//	@GetMapping("productReview/{no}")
//	public ArrayList<Review> selectReview(@PathVariable("no") int productNo){
//		return pService.selectReview(productNo);
//	}
}
