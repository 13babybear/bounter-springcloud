package cn.bounter.simon.controller;


import cn.bounter.common.model.ResponseData;
import cn.bounter.common.util.IdGenerator;
import cn.bounter.simon.model.po.Bounter;
import cn.bounter.simon.service.BounterService;
import cn.bounter.simon.service.SusanService;
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

	@Autowired
	private SusanService susanService;


	@PostMapping
	@TxTransaction(isStart = true)				//开启分布式事务
	@Transactional								//开启分布式事务
	public ResponseData<?> add() {

		//调用本地服务
		bounterService.add(new Bounter().setId(IdGenerator.getId()).setName("Simon"));

		//调用远程服务
		susanService.add();

		//模拟抛出异常，测试分布式事务回滚
//		if (1 == 1) {
//			throw new RuntimeException("test");
//		}

		return new ResponseData<>().success();
	}

}
