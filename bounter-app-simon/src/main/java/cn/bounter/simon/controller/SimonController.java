package cn.bounter.simon.controller;


import cn.bounter.common.model.ResponseData;
import cn.bounter.simon.service.SusanService;
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
		return new ResponseData<>().success().data(susanService.getSusanName().getData());
	}

}
