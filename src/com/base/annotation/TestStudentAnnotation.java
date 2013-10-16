package com.base.annotation;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

public class TestStudentAnnotation {
	private static Logger log=Logger.getLogger(TestStudentAnnotation.class);
	  public static void main(String[] args) throws ClassNotFoundException {
		Class<?> clz=Class.forName("com.base.annotation.Student");
		 Method[] method=clz.getMethods();
		boolean flag= clz.isAnnotationPresent(Description.class);
		if(flag){
			Description des=clz.getAnnotation(Description.class);
			String value=des.value();
			log.info("class description--"+value);
		}
		Set<Method> set=new HashSet<Method>();
		for (Method m : method) {
			boolean mFlag=m.isAnnotationPresent(UserInfo.class);
			if(mFlag){
				set.add(m);
			}
		}
		for (Method mt : set) {
			UserInfo uinfo=mt.getAnnotation(UserInfo.class);
			String userName=uinfo.userName();
			int age=uinfo.age();
			String community=uinfo.community();
			String major=uinfo.major();
			log.info(community+"--"+userName+"--"+age+"--"+major);
		}
	}
}
