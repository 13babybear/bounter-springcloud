package cn.bounter.oauth2.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Client implements Serializable {
	
	private static final Long serialVersionUID = 1L;

	private Long id;
	
	private String clientName;
	
	private String clientId;
	
	private String clientSecret;

	private Integer accessTokenValiditySeconds;

	private Integer refreshTokenValiditySeconds;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date updateTime;

    //客户端回调地址
    private List<String> redirectUris;

    public Long getId() {
        return id;
    }

    public Client setId(Long id) {
        this.id = id;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public Client setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Client setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public Client setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        return this;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public Client setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public Client setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Client setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Client setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public List<String> getRedirectUris() {
        return redirectUris;
    }

    public Client setRedirectUris(List<String> redirectUris) {
        this.redirectUris = redirectUris;
        return this;
    }
}
