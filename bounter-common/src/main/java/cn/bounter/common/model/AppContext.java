package cn.bounter.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 应用上下文
 * @author simon
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppContext implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//事务关联ID
	private Long correlationId;
	
	private String token;

	public Long getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(Long correlationId) {
		this.correlationId = correlationId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
