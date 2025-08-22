package itView.springboot.exception;

public class ProductException extends RuntimeException {
	public ProductException() {}
	public ProductException(String msg) {
		super(msg);
	}
}
