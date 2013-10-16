package com.base.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.ui.velocity.VelocityEngineUtils;
/**
 * 
 * @author lucene_yang
 * @date 2010-5-19下午08:37:02
 * @project_name gamechannel
 *
 */
public class VelocityUtils {
	
	private VelocityEngine velocityEngine;

	private VelocityEngineFactory velocityEngineFactory;


	/**
	 * @return Returns the velocityEngine.
	 */
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	/**
	 * @param velocityEngine
	 *            The velocityEngine to set.
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/**
	 * 根据obj的类名找到对应模板 obj.getClass()
	 * 
	 * @param obj
	 * @param writer
	 */
	public void serialize(Object obj, Map model, Writer writer) {
		if (obj != null) {
			String vmName = getTemplateName(obj.getClass());
			this.serialize(vmName, model, writer);
		}
	}

	public void serialize(String vmName,Map model, Writer writer) {
		try {
			VelocityEngineUtils.mergeTemplate(velocityEngine, vmName, model, writer);
		} catch (VelocityException e) {
			e.printStackTrace();
		}
	}
	
	public String mergeTemplateIntoString(String vmName,Map model){
		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,vmName,model);
	}

	private static String getTemplateName(Class clazz) {
		String name = clazz.getName();
		name = name.replace('.', '/');
		return name + ".vm";
	}


	public VelocityEngineFactory getVelocityEngineFactory() {
		return velocityEngineFactory;
	}

	public void setVelocityEngineFactory(VelocityEngineFactory velocityEngineFactory) {
		this.velocityEngineFactory = velocityEngineFactory;
		try {
			velocityEngine = velocityEngineFactory.createVelocityEngine();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (VelocityException e) {
			e.printStackTrace();
		}
	}
}
