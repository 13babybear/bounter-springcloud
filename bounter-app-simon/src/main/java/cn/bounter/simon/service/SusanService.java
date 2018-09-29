package cn.bounter.simon.service;

import cn.bounter.common.model.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("susan")           //指定客户端在Eureka中注册的名称
public interface SusanService {

    @GetMapping("/api/susan/name")
    public ResponseData<?> getSusanName();
}
