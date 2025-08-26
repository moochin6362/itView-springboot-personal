package itView.springboot.exception;

public class LoginException extends RuntimeException {
	public LoginException() {}
	public LoginException(String msg) {
		super(msg);
	}
}
