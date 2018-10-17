package cn.bounter.simon.lcn;

import com.codingapi.tx.config.service.TxManagerTxUrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 自定义tx-manager Url地址配置
 */
@Service
public class TxManagerTxUrlServiceImpl implements TxManagerTxUrlService {

    @Value("${tm.manager.url}")
    private String url;

    @Override
    public String getTxUrl() {
        return url;
    }
}
