package com.base.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

public class AuthProxyFilter implements CallbackFilter{
	public int accept(Method arg0) {
		if(!"query".equalsIgnoreCase(arg0.getName()))
			return 0;
		return 1;
	}

}
