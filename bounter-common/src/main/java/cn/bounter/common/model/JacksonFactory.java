package cn.bounter.common.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

/**
 * Jackson Mapper工厂
 * @author simon
 *
 */
public class JacksonFactory {
	
	private static volatile ObjectMapper mapper = null;
	
	private JacksonFactory(){}
	
	public static ObjectMapper getMapper() {
		if (mapper == null) {
            synchronized (JacksonFactory.class) {
                if (mapper == null) {
                	mapper = new ObjectMapper();
                	//日期格式化
                	mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
            }
        }
        return mapper;
	}
}
