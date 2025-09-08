package itView.springboot.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import itView.springboot.vo.Attachment;
import itView.springboot.vo.Coupon;
import itView.springboot.vo.Product;
import itView.springboot.vo.Review;
import itView.springboot.vo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@Mapper
public interface ProductMapper {

	int insertProduct(Product product);

	ArrayList<Product> selectMyProduct(Product product);

	int editStock(Product product);

	int insertCoupon(Coupon coupon);

	ArrayList<Coupon> selectMyCoupon(int userNo);

	int editCoupon(Coupon coupon);

	int insertAttm(Attachment attm);

	Attachment selectMyAttm(int productNo);

	int selectProductSeq();

	int editProduct(Product product);

	int deleteMyAttm(int attmNo);

	int deleteProduct(int productNo);

	int deleteAttm(int productNo);

	List<Product> getLatestProducts();

	List<Product> getSearchProducts(@Param("rowBounds") RowBounds rowBounds, String q);

	List<Product> getfilterProducts(String q, List<String> categories);

	int getListCountWithSearch(String q);

	// Mapper.java
	List<Product> getFilterProducts(@Param("rowBounds") RowBounds rowBounds,
									@Param("q") String q,
									@Param("categories") List<String> categories);

	int getListCountWithFilter(@Param("q") String q,
							   @Param("categories") List<String> categories);

	Product selectMyProductDetail(Product product);

	ArrayList<Review> selectReview(int productNo);
}

