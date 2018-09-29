package cn.bounter.common.extension;

import cn.bounter.common.model.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//监听所有控制器从@RequestMapping方法抛出的异常
@RestControllerAdvice
public class ControllerExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
    public ResponseData<?> handleException(Exception e) {
		logger.error(e.getMessage(),e);
		return new ResponseData<>().fail("系统异常，请稍后再试！");
    }
}
