package cn.bounter.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 用户身份实体
 * @author simon
 *
 */
public class Subject {
	
	//用户id
	private String id;
	
	//用户名
	private String username;
	
	//用户角色
	private String role;
	
	//应用
	private String appId;
	
	//用户ip
	private String ip;
	
	//上次登录时间
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date lastTime;
	
	//上次登录地址
	private String lastAddr;

	public String getId() {
		return id;
	}

	public Subject setId(String id) {
		this.id = id;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public Subject setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getRole() {
		return role;
	}

	public Subject setRole(String role) {
		this.role = role;
		return this;
	}

	public String getIp() {
		return ip;
	}

	public Subject setIp(String ip) {
		this.ip = ip;
		return this;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public Subject setLastTime(Date lastTime) {
		this.lastTime = lastTime;
		return this;
	}

	public String getLastAddr() {
		return lastAddr;
	}

	public Subject setLastAddr(String lastAddr) {
		this.lastAddr = lastAddr;
		return this;
	}

	public String getAppId() {
		return appId;
	}

	public Subject setAppId(String appId) {
		this.appId = appId;
		return this;
	}

}
