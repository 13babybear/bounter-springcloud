package cn.bounter.oauth2.security;

import cn.bounter.oauth2.dao.ClientDao;
import cn.bounter.oauth2.dao.ClientRedirectUriDao;
import cn.bounter.oauth2.model.po.Client;
import cn.bounter.oauth2.model.po.ClientRedirectUri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private ClientRedirectUriDao clientRedirectUriDao;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        //查询客户端基本信息
        Client client = clientDao.selectOne(new Client().setClientId(clientId));
        if (client == null) {
            throw new ClientRegistrationException("Client not found!");
        }

        //查询客户端回调地址
        Set<String> redirectUris = clientRedirectUriDao.selectAll(new ClientRedirectUri().setClientId(clientId))
                                                       .stream()
                                                       .map(clientRedirectUri -> clientRedirectUri.getRedirectUri())
                                                       .collect(Collectors.toSet());

        return new ClientDetailsImpl(client, redirectUris);
    }
}
