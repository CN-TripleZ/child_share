package com.ling.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.ling.framework.database.NotDBField;

public class ReflectionUtil {

	public static Object invokeMethod(String className, String methodName,
			Object[] args) {
		try {
			Class<?> serviceClass = Class.forName(className);
			Object service = serviceClass.newInstance();
			Class<?>[] argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {
				argsClass[i] = args[i].getClass();
			}
			Method method = serviceClass.getMethod(methodName, argsClass);
			return method.invoke(service, args);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将pojo对象中有属性和值转换成map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> pojo2Map(Object pojo) {
		Map<String, Object> pojoMap = new HashMap<String, Object>();
		Map<String, Object> map     = new HashMap<String, Object>();
		try {
			map = BeanUtils.describe(pojo);
		} catch (Exception ex) {
		}
		Object[] keys = map.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			String str = keys[i].toString();
			if (str != null && !str.equals("class")) {
				if (map.get(str) != null) {
					pojoMap.put(str, map.get(str));
				}
			}
		}
		Method[] methods = pojo.getClass().getMethods();
		for (Method m : methods) {
			String name = m.getName();
			if (name.startsWith("get")) {
				if (m.getAnnotation(NotDBField.class) != null) {
					pojoMap.remove(getFieldName(name));
				}
			}

		}
		return pojoMap;
	}

	private static String getFieldName(String methodName) {
		methodName = methodName.replaceAll("get", "");
		methodName = methodName.substring(0, 1).toLowerCase()
				+ methodName.substring(1);
		return methodName;
	}
}
