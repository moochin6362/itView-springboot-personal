package itView.springboot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import itView.springboot.vo.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import itView.springboot.mapper.ProductMapper;
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
		return mapper.selectReview(productNo);
	}

	public Product selectMyProductDetail(Product product) {
		return mapper.selectMyProductDetail(product);
	}

	public List<Product> getSearchProducts(PageInfo pi, String q) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.getSearchProducts(rowBounds, q);
	}

	public List<Product> getfilterProducts(String q, List<String> categories) {
		return mapper.getfilterProducts(q, categories);
	}

	public int getListCountWithSearch(String q) {
		return mapper.getListCountWithSearch(q);
	}

	// 게시글 수 가져오기
	public int getListCountWithFilter(String q, List<String> categories) {
		return mapper.getListCountWithFilter(q, categories);
	}

	// 페이징 적용해서 검색
	public List<Product> getFilterProducts(PageInfo pi, String q, List<String> categories) {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		return mapper.getFilterProducts(rowBounds, q, categories);
	}

}
