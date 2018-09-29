package cn.bounter.oauth2.common;

/**
 * 角色枚举类
 * @author simon
 *
 */
public enum RoleEnum {
	/** 客户端应用 */
	ROLE_CLIENT("ROLE_CLIENT","客户端应用"),
	/** 注册用户 */
	ROLE_MEMBER("ROLE_MEMBER","注册用户"),
	/** 客服 */
	ROLE_STAFF("ROLE_STAFF","客服"),
	/** 超级管理员 */
	ROLE_ADMIN("ROLE_ADMIN","超级管理员");
	
	private String value;
	
	private String name;
	
	private RoleEnum(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * 自定义方法
	 * 根据枚举值获取枚举名字
	 * @param value
	 * @return
	 */
	public static String nameOf(String value) {
		for(RoleEnum oneEnum : RoleEnum.values()) {
			if(oneEnum.value == value) {
				return oneEnum.getName();
			}
		}
		return null;
	}

}
