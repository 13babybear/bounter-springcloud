package cn.bounter.oauth2.model.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRole implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String username;
	
	private String role;

    public Long getId() {
        return id;
    }

    public UserRole setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRole setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserRole setRole(String role) {
        this.role = role;
        return this;
    }
}
