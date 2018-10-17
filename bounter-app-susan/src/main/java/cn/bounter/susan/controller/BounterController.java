package cn.bounter.susan.controller;


import cn.bounter.common.model.ResponseData;
import cn.bounter.common.util.IdGenerator;
import cn.bounter.susan.po.Bounter;
import cn.bounter.susan.service.BounterService;
import com.codingapi.tx.annotation.TxTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/bounter")
public class BounterController {

	private static final Logger logger = LoggerFactory.getLogger(BounterController.class);

	@Autowired
	private BounterService bounterService;


	@PostMapping
	@TxTransaction		//开启分布式事务
	@Transactional		//开启分布式事务
	public ResponseData<?> add() {

		bounterService.add(new Bounter().setId(IdGenerator.getId()).setName("Susan"));

		return new ResponseData<>().success();
	}

}
