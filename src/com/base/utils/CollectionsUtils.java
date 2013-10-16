package com.base.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
/**
 * 返回集合的第一个元素
 * @author lucene_yang
 * @date 2010-5-19下午08:36:16
 * @project_name gamechannel
 *
 */
public class CollectionsUtils {
	
  /**
   * 
   * @param <T>
   * @param list
   * @return
   */
	public static <T> T getFirstElementFromList(List<T> list)
	{
		return list.size()>0?list.get(0):null;
	}
	
	/**
	 * 集合排序
	 */
	public static <T> List<T> sort(List<T> list,final String propertyName,final boolean isDesc){
		Collections.sort(list, new Comparator<T>(){
			public int compare(T o1, T o2) {
				String p1=ObjectUtils.toString(GenericsUtils.getPropertyValue(o1, propertyName));
				String p2=ObjectUtils.toString(GenericsUtils.getPropertyValue(o2, propertyName));
				return !isDesc?p1.compareTo(p2):p2.compareTo(p1);
			}
		});
		return list;
	}
}
