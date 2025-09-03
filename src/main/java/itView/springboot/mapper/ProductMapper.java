package itView.springboot.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import itView.springboot.vo.Attachment;
import itView.springboot.vo.Coupon;
import itView.springboot.vo.Product;
import itView.springboot.vo.User;

@Mapper
public interface ProductMapper {

	User login(User u);

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

	

}

