package cn.bounter.simon.controller;


import cn.bounter.common.model.ResponseData;
import cn.bounter.simon.service.SusanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/simon")
public class SimonController {

	private static final Logger logger = LoggerFactory.getLogger(SimonController.class);

	@Value("${name}")
	private String name;

	@Autowired
	private SusanService susanService;

	@GetMapping("/name")
	public ResponseData<?> get() {
		return new ResponseData<>().success().data(name);
	}

	@GetMapping("/susan/name")
	public ResponseData<?> getSusan() {
		logger.info("test");
		return new ResponseData<>().success().data(susanService.getSusanName().getData());
	}

}
