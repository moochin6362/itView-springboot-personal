package itView.springboot.exception;

public class ShoppingException extends RuntimeException {
	public ShoppingException() {}
	public ShoppingException(String msg) {
		super(msg);
	}
}
