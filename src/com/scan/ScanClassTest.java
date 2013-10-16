package com.scan;

import java.lang.reflect.Modifier;

import junit.framework.TestCase;

public class ScanClassTest  extends TestCase{
		private ClassFilter filter;
		@Override
		protected void setUp(){
			 filter = new ClassFilter(){
				@Override
				public boolean accept(Class<?> clazz) {
					return !Modifier.isAbstract(clazz.getModifiers())
						  && !Modifier.isInterface(clazz.getModifiers())
						  && Modifier.isPublic(clazz.getModifiers())
						  && !Modifier.isStatic(clazz.getModifiers())
						  && Person.class.isAssignableFrom(clazz);
				}
			 };
		}
		public void testScanClassTest() throws Exception{
				for(Class<?> clazz : ClassUtils.scanPackage("core",filter)){
					System.out.println(clazz);
				}
				
		}
}
