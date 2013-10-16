package com.base.utils;


import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ELException;

import org.apache.jasper.runtime.PageContextImpl;
import org.apache.log4j.Logger;

public class JspUtils {
	private static Logger log=Logger.getLogger(JspUtils.class);
	private static final String PREFIX="${";
	private static final String POSTFIX="}";
	
	public static <T> T getPropertyStringFromEL(String elName,PageContext page,Class<T> cls){
		try {
			if(!elName.startsWith(PREFIX))
				elName=PREFIX+elName;
			if(!elName.endsWith(POSTFIX))
				elName=elName+POSTFIX;
			if(cls==null)cls=(Class<T>) String.class;
			T result=(T) PageContextImpl.proprietaryEvaluate(elName,cls, page, null, false);
			return (T)result;
		} catch (ELException e) {
			log.error("", e);
		}
		return null;
	}
}
