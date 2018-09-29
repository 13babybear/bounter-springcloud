package cn.bounter.oauth2.security;

import cn.bounter.oauth2.common.RoleEnum;
import cn.bounter.oauth2.model.po.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * 自定义客户端信息实现
 */
public class ClientDetailsImpl implements ClientDetails {

    private static final int DEFAULT_ACCESS_TOKEN_VALIDITY_SECONDS = 1800;
    private static final int DEFAULT_REFRESH_TOKEN_VALIDITY_SECONDS = 604800;

    private Client client;
    private Set<String> redirectUris;


    /**
     * @param client            客户端基本信息
     * @param redirectUris      客户端重定向地址
     */
    public ClientDetailsImpl(Client client, Set<String> redirectUris) {
        this.client = client;
        this.redirectUris = redirectUris;
    }

    @Override
    public String getClientId() {
        return client.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return null;
    }

    @Override
    public boolean isSecretRequired() {
        return false;
    }

    @Override
    public String getClientSecret() {
        return client.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        return null;
    }

    /**
     * 默认支持除implicit之外的所有类型
     * @return
     */
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> grantTypes = new HashSet<>();
        grantTypes.add("authorization_code");
        grantTypes.add("password");
        grantTypes.add("client_credentials");
        grantTypes.add("refresh_token");
        return grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return redirectUris;
    }

    /**
     * 客户端所拥有的权限，默认“ROLE_CLIENT”
     * @return
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(RoleEnum.ROLE_CLIENT.getValue()));
        return authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return client.getAccessTokenValiditySeconds() == null ? DEFAULT_ACCESS_TOKEN_VALIDITY_SECONDS : client.getAccessTokenValiditySeconds();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return client.getRefreshTokenValiditySeconds() == null ? DEFAULT_REFRESH_TOKEN_VALIDITY_SECONDS : client.getRefreshTokenValiditySeconds();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
