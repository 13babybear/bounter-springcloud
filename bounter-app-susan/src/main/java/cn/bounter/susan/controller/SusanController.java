package cn.bounter.susan.controller;


import cn.bounter.common.model.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/susan")
public class SusanController {

	private static final Logger logger = LoggerFactory.getLogger(SusanController.class);

	@Value("${name}")
	private String name;
	
	@GetMapping("/name")
	public ResponseData<?> get() {
		logger.info("test");
		try {
			//测试Hystrix
			Thread.sleep(2000);
		} catch (InterruptedException e) {}

		return new ResponseData<>().success().data(name);
	}

}
