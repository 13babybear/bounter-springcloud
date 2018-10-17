package cn.bounter.oauth2.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String username;
	
	private String password;

    private String clientId;

	private Boolean isStaff;

	private List<String> roles;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private String traceId;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public User setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public User setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public Boolean getIsStaff() {
        return isStaff;
    }

    public User setIsStaff(Boolean isStaff) {
        this.isStaff = isStaff;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public User setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getTraceId() {
        return traceId;
    }

    public User setTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }
}
