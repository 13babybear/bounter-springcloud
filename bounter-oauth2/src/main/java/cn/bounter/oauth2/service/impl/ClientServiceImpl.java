package cn.bounter.oauth2.service.impl;


import cn.bounter.common.model.BaseServiceImpl;
import cn.bounter.common.util.IdGenerator;
import cn.bounter.oauth2.dao.ClientRedirectUriDao;
import cn.bounter.oauth2.model.po.Client;
import cn.bounter.oauth2.model.po.ClientRedirectUri;
import cn.bounter.oauth2.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl extends BaseServiceImpl<Client> implements ClientService {

    @Autowired
    private ClientRedirectUriDao clientRedirectUriDao;

    @Override
    @Transactional
    public void add(Client client) {
        //新增客户端
        super.add(client);

        //新增客户端回调地址
        List<ClientRedirectUri> clientRedirectUris = client.getRedirectUris().stream()
                                                                             .map(redirectUri -> new ClientRedirectUri().setId(IdGenerator.getId())
                                                                                                                        .setClientId(client.getClientId())
                                                                                                                        .setRedirectUri(redirectUri))
                                                                             .collect(Collectors.toList());
        clientRedirectUriDao.batchInsert(clientRedirectUris);
    }
}
