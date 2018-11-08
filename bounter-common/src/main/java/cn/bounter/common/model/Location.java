package cn.bounter.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * IP位置信息
 * @author simon
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//省
	private String province;
	
	//城市
	private String city;

	public String getProvince() {
		return province;
	}

	public Location setProvince(String province) {
		this.province = province;
		return this;
	}

	public String getCity() {
		return city;
	}

	public Location setCity(String city) {
		this.city = city;
		return this;
	}

	@Override
	public String toString() {
		return province + city;
	}
}
