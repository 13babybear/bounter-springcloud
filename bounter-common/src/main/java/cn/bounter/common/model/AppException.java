package cn.bounter.common.model;

/**
 * 应用自定义异常
 * @author simon
 *
 */
public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
