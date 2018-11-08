package cn.bounter.common.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理器
 * 子类只需要继承该类，并在类上添加@RestControllerAdvice就可以进行异常统一处理
 */
public class BaseExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
    public ResponseData<?> handleException(Exception e) {
		logger.error(e.getMessage(),e);
		return new ResponseData<>().fail("系统异常，请稍后再试！");
    }
}
