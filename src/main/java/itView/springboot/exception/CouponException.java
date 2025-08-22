package itView.springboot.exception;

public class CouponException extends RuntimeException {
	public CouponException() {}
	public CouponException(String msg) {
		super(msg);
	}
}
