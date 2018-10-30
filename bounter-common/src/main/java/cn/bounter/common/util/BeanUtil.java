package cn.bounter.common.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实体工具类
 */
public class BeanUtil {

    /**
     * 对象转Map,所有的Long类型会被转成String
     * @param obj
     * @param includeFields         包含的属性
     * @param excludeFields         排除的属性
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, Object> toMap(Object obj, String[] includeFields, String[] excludeFields) {
        if(obj == null) {
            return null;
        }

        Map<String, Object> map = null;
        try {
            map = new HashMap<>();
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors();
            List<String> includeList = (includeFields != null ? Arrays.asList(includeFields) : null);
            List<String> excludeList = (excludeFields != null ? Arrays.asList(excludeFields) : null);
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String key = propertyDescriptor.getName();
                if (includeList == null) {
                    if (excludeList != null && excludeList.contains(key)) {
                        continue;
                    }
                } else {
                    if (!includeList.contains(key)) {
                        continue;
                    }
                }

                // 过滤class属性
                if (!key.equalsIgnoreCase("class")) {
                    // 得到property对应的getter方法
                    Method getter = propertyDescriptor.getReadMethod();
                    Object value = getter!=null ? getter.invoke(obj) : null;
                    //自动把long类型转成String,解决js中Long过长丢失精度的问题
                    if (value instanceof Long) {
                        value = String.valueOf(value);
                    }
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 对象转map
     * @param obj
     * @return
     */
    public static Map<String, Object> toMap(Object obj) {
        return toMap(obj, null, null);
    }

    /**
     * 对象转map
     * @param obj
     * @param includeFields         包含的属性
     * @return
     */
    public static Map<String, Object> toMap(Object obj, String[] includeFields) {
        return toMap(obj, includeFields, null);
    }

}
