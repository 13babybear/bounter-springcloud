package cn.bounter.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * IP地址
 * @author simon
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpAddress implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String ip;
	
	//国家
	private String country;
	
	//区域
	private String area;
	
	//省
	private String region;
	
	//城市
	private String city;
	
	//县
	private String county;
	
	private String isp;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

}
