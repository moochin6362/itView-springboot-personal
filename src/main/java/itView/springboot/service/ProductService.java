package itView.springboot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import itView.springboot.mapper.ProductMapper;
import itView.springboot.vo.Attachment;
import itView.springboot.vo.Coupon;
import itView.springboot.vo.Product;
import itView.springboot.vo.Review;
import itView.springboot.vo.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductMapper mapper;


	public int insertProduct(Product product) {
		return mapper.insertProduct(product);
	}

	public ArrayList<Product> selectMyProduct(Product product) {
		return mapper.selectMyProduct(product);
	}

	public int editStock(Product product) {
		return mapper.editStock(product);
	}

	public int insertCoupon(Coupon coupon) {
		return mapper.insertCoupon(coupon);
	}

	public ArrayList<Coupon> selectMyCoupon(int userNo) {
		return mapper.selectMyCoupon(userNo);
	}

	public int editCoupon(Coupon coupon) {
		return mapper.editCoupon(coupon);
	}

	public int insertAttm(Attachment attm) {
		return mapper.insertAttm(attm);
	}

	public Attachment selectMyAttm(int productNo) {
		return mapper.selectMyAttm(productNo);
	}

	public int selectProductSeq() {
		return mapper.selectProductSeq();
	}

	public int editProduct(Product product) {
		return mapper.editProduct(product);
	}

	public int deleteMyAttm(int attmNo) {
		return mapper.deleteMyAttm(attmNo);
	}

	public int deleteProduct(int productNo) {
		return mapper.deleteProduct(productNo);
	}

	public int deleteAttm(int productNo) {
		return mapper.deleteAttm(productNo);
	}


    public List<Product> getLatestProducts() {
		return mapper.getLatestProducts();
    }

	public ArrayList<Review> selectReview(int productNo) {
		// TODO Auto-generated method stub
		return null;
	}
}
