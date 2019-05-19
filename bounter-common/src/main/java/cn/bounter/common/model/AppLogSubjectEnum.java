package cn.bounter.common.model;

/**
 * 应用日志主题枚举类
 * @author simon
 *
 */
public enum AppLogSubjectEnum {

	/** 客户 */
	CUSTOMER(1,"客户"),
	/** 订单 */
	COMMODITY(2,"商品"),
	/** 订单 */
	ORDER(3,"订单");

	private Integer value;

	private String name;

	private AppLogSubjectEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * 自定义方法
	 * 根据枚举值获取枚举字符串内容
	 * @param value
	 * @return
	 */
	public static String stringOf(int value) {
		for(AppLogSubjectEnum oneEnum : AppLogSubjectEnum.values()) {
			if(oneEnum.value == value) {
				return oneEnum.getName();
			}
		}
		return null;
	}

}
