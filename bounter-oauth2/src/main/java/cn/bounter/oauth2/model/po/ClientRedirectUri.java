package cn.bounter.oauth2.model.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRedirectUri implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String clientId;
	
	private String redirectUri;

    public Long getId() {
        return id;
    }

    public ClientRedirectUri setId(Long id) {
        this.id = id;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public ClientRedirectUri setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public ClientRedirectUri setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }
}
