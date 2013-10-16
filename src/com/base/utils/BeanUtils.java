package com.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * 扩展Apache Commons BeanUtils, 提供一些反射方面缺失功能的封装.
 * 
 * @author lucene_yang
 * @date 2010-5-19下午08:35:54
 * @project_name gamechannel
 * 
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

	protected static final Logger logger = Logger.getLogger(BeanUtils.class);

	private BeanUtils() {
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
	}

	/**
	 * 暴力获取对象变量值,忽略private,protected修饰符的限制.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.info("error wont' happen");
		}
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力设置对象变量值,忽略private,protected修饰符的限制.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static void forceSetProperty(Object object, String propertyName, Object newValue)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			logger.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}

	/**
	 * 暴力调用对象函数,忽略private,protected修饰符的限制.
	 * 
	 * @throws NoSuchMethodException
	 *             如果没有该Method时抛出.
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object... params)
			throws NoSuchMethodException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}

		Class clazz = object.getClass();
		Method method = null;
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			} catch (NoSuchMethodException e) {
				// 方法不在当前类定义,继续向上转型
			}
		}

		if (method == null)
			throw new NoSuchMethodException("No Such Method:" + clazz.getSimpleName() + methodName);
		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try {
			result = method.invoke(object, params);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		method.setAccessible(accessible);
		return result;
	}

	/*
	 * public void test(Class clz,int count){ Class cls; try { cls =
	 * Class.forName("BeanUtils.class"); Class[] cl = new Class[count];//3是参数个数
	 * cl[0] = String.class; cl[1] = int.class; cl[2] = Object.class; Method m =
	 * cls.getDeclaredMethod("doPrint",cl); } catch (ClassNotFoundException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */
	public static String test(Integer code, String name) {
		String str = "名字---" + name + "薪水" + code;
		return str;
	}
	public static void main(String[] args) {
		int salery = 10000000;
		try {
			
			getMethodOrParameterType("com.woyo.gamechannel.core.utils.BeanUtils", new Object[] { salery, "yangfuchao" });
		} catch (Exception e) {
			logger.error("call method error\t" + e);
		}
	}

	public static void getMethodOrParameterType(String clz, Object... params) {
		try {
			Class cls = Class.forName(clz);
			// 共有两个参数
			Class[] types = new Class[params.length];// 该方法有多少个参数
			for (int i = 0; i < params.length; i++) {
				types[i] = params[i].getClass();
			}
			//此处表示参数的类型而不是具体的参数，所以上面才有 params[i].getClass()，下面invoke时才是
			Method decMethod = cls.getDeclaredMethod("test", types);
			BeanUtils bean=new BeanUtils();//声明调用的实例
			Object obj=decMethod.invoke(bean, params);
			logger.info("调用的方法名称为---" + decMethod.getName());
			logger.info("调用的方法返回类型为---" + decMethod.getReturnType().getName());
			logger.info("调用的方法返回值为---" + obj);
			int k = 0;
			for (Class<?> method : decMethod.getParameterTypes()) {
				logger.info("调用的方法第" + k + "个参数类型为---" + method.getName());
				k++;
			}
		} catch (ClassNotFoundException e) {
			logger.error("call method error\t" + e);
		} catch (SecurityException e) {
			logger.error("call method error\t" + e);
		} catch (NoSuchMethodException e) {
			logger.error("call method error\t" + e);
		} catch (IllegalArgumentException e) {
			logger.error("call method error\t" + e);
		} catch (IllegalAccessException e) {
			logger.error("call method error\t" + e);
		} catch (InvocationTargetException e) {
			logger.error("call method error\t" + e);
		}
	}

	/**
	 * 反射得到方法参数列表类型，方法名
	 */
	public static void getMethodParameterType(String clz) throws Exception {
		// 得到String类对象
		Class cls = Class.forName(clz);
		// 得到所有的方法，包括从父类继承过来的方法
		Method[] methList = cls.getMethods();
		// 下面是得到的是String类本身声明的方法
		// Method []methList=cls.getDeclaredMethods();
		// 遍历所有的方法
		for (Method m : methList) {
			// 方法名
			logger.info("方法名=" + m.getName());
			// 方法声明所在的类
			logger.info("声明的类=" + m.getDeclaringClass());
			// 获取所有参数类型的集体
			Class[] paramTypes = m.getParameterTypes();
			// 遍历参数类型
			for (int i = 0; i < paramTypes.length; i++) {
				logger.info("参数 " + i + " = " + paramTypes[i]);
			}
			// 获取所有异常的类型
			Class[] excepTypes = m.getExceptionTypes();
			// 遍历异常类型
			for (int j = 0; j < excepTypes.length; j++) {
				logger.info("异常 " + j + " = " + excepTypes[j]);
			}
			// 方法的返回类型
			logger.info("返回类型 =" + m.getReturnType());
			// 结束一层循环标志
			logger.info("---------");
		}
	}

	/**
	 * 按Filed的类型取得Field列表.
	 */
	
	public static List<Field> getFieldsByType(Object object, Class type) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * 按Filed的类型取得Field列表.
	 */
	public static List<Field> getClassFields(Object object) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			list.add(field);
		}
		return list;
	}

	// public static void main(String[] args) {
	// TopicDTO d=new TopicDTO();
	// List<Field> list=getFieldsByType(d, TopicDTO.class);
	// logger.info(list);
	// }
	/**
	 * 按FiledName获得Field的类型.
	 */
	public static Class getPropertyType(Class type, String name) throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}

	/**
	 * 获得field的getter函数名称.
	 */
	public static String getGetterName(Class type, String fieldName) {
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");

		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	/**
	 * 获得field的getter函数,如果找不到该方法,返回null.
	 */
	public static Method getGetterMethod(Class type, String fieldName) {
		try {
			return type.getMethod(getGetterName(type, fieldName));
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 扩展copyProperties方法，用于JavaBean属性的拷贝
	 * 
	 * @param dest
	 *            目标对象
	 * @param orig
	 *            源对象
	 */
	public static void copyPropertiesExtends(Object dest, Object orig) {
		Method[] methods = orig.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().startsWith("get")) {
				try {
					Object obj = methods[i].invoke(orig, new Object[] {});
					if (obj != null) {
						String getMethodName = methods[i].getName();
						String setMethodName = getMethodName.replace("get", "set");
						Method[] destMethods = dest.getClass().getDeclaredMethods();
						for (int j = 0; j < destMethods.length; j++) {
							if (setMethodName.equals(destMethods[j].getName())) {
								String propertyName = getMethodName.replace("get", "");
								propertyName = propertyName.substring(0, 1).toLowerCase() + propertyName.substring(1);
								PropertyUtils.setProperty(dest, propertyName, obj);
							}

						}
					}
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
}
