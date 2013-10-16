package com.base.utils;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;


public class JsonUtil {

	  /** *//**
   * 从一个JSON 对象字符格式中得到一个java对象
   * @param jsonString
   * @param pojoCalss
   * @return
   */
  public static Object getObject4JsonString(String jsonString,Class pojoCalss){
      Object pojo;
      JSONObject jsonObject = JSONObject.fromObject( jsonString ); 
      pojo = JSONObject.toBean(jsonObject,pojoCalss);
      return pojo;
  }
  /** *//**
   * 从json HASH表达式中获取一个map，改map支持嵌套功能
   * @param jsonString
   * @return
   */
  public static Map getMap4Json(String jsonString){
      JSONObject jsonObject = JSONObject.fromObject( jsonString );      
      return getMap4Json(jsonObject);
  }
 
  public static Map getMap4Json(JSONObject jsonObject){

      Iterator  keyIter = jsonObject.keys();
      String key;
      Object value;
      Map valueMap = new HashMap();

      while( keyIter.hasNext())
      {
          key = (String)keyIter.next();
          value = jsonObject.get(key);
          valueMap.put(key, parseJson(value));
      }
     
      return valueMap;
  }   
  /** *//**
   * 从json数组中得到相应java数组
   * @param jsonString
   * @return
   */
  public static Object[] getObjectArray4Json(String jsonString){
      JSONArray jsonArray = JSONArray.fromObject(jsonString);
      return jsonArray.toArray();
     
  }
 
 
  /** *//**
   * 从json对象集合表达式中得到一个java对象列表
   * @param jsonString
   * @param pojoClass
   * @return
   */
  public static List getList4Json(String jsonString, Class pojoClass){       
      JSONArray jsonArray = JSONArray.fromObject(jsonString);
      JSONObject jsonObject;
      Object pojoValue;
      
      List list = new ArrayList();
      for ( int i = 0 ; i<jsonArray.size(); i++){          
          jsonObject = jsonArray.getJSONObject(i);
          pojoValue = JSONObject.toBean(jsonObject,pojoClass);
          list.add(pojoValue);          
      }
      return list;
  }
 
  public static List getList4Json(String jsonString){       
      JSONArray jsonArray = JSONArray.fromObject(jsonString);
      return getList4Json(jsonArray);
  }
 
  public static List getList4Json(JSONArray jsonArray){       
      Object value;
     
      List list = new ArrayList();
      for ( int i = 0 ; i<jsonArray.size(); i++){        
      	value = jsonArray.get(i);         
          list.add(parseJson(value));         
      }
      return list;
  }
  
  public static String java2json(Object pojo){
	  try {
		String json=JSONArray.fromObject(pojo).toString();
		 return json;
     	} catch (Exception e) {
     	e.printStackTrace();
		return null;
	  }
  }
  
  /** *//**
   * 从json数组中解析出java字符串数组
   * @param jsonString
   * @return
   */
  public static String[] getStringArray4Json(String jsonString){
     
      JSONArray jsonArray = JSONArray.fromObject(jsonString);
      String[] stringArray = new String[jsonArray.size()];
      for( int i = 0 ; i<jsonArray.size() ; i++ ){
          stringArray[i] = jsonArray.getString(i);
         
      }
     
      return stringArray;
  }
 
  /** *//**
   * 从json数组中解析出javaLong型对象数组
   * @param jsonString
   * @return
   */
  public static long[] getLongArray4Json(String jsonString){
     
      JSONArray jsonArray = JSONArray.fromObject(jsonString);
      long[] longArray = new long[jsonArray.size()];
      for( int i = 0 ; i<jsonArray.size() ; i++ ){
          longArray[i] = jsonArray.getLong(i);
         
      }
      return longArray;
  }
 
  /** *//**
   * 从json数组中解析出java Integer型对象数组
   * @param jsonString
   * @return
   */
  public static int[] getIntegerArray4Json(String jsonString){
     
      JSONArray jsonArray = JSONArray.fromObject(jsonString);
      int[] integerArray = new int[jsonArray.size()];
      for( int i = 0 ; i<jsonArray.size() ; i++ ){
          integerArray[i] = jsonArray.getInt(i);
         
      }
      return integerArray;
  }
  
  /** *//**
   * 从json数组中解析出java Integer型对象数组
   * @param jsonString
   * @return
   */
  public static double[] getDoubleArray4Json(String jsonString){
     
      JSONArray jsonArray = JSONArray.fromObject(jsonString);
      double[] doubleArray = new double[jsonArray.size()];
      for( int i = 0 ; i<jsonArray.size() ; i++ ){
          doubleArray[i] = jsonArray.getDouble(i);
         
      }
      return doubleArray;
  }
 
 
  /** *//**
   * 将java对象转换成json字符串
   * @param javaObj
   * @return
   */
  public static String getJsonString4JavaPOJO(Object javaObj){
     
      JSONObject json;
      json = JSONObject.fromObject(javaObj);
      return json.toString();
     
  }
  
  /** *//**
   * JSON 时间解析器具
   * @param datePattern
   * @return
   */
  public static JsonConfig configJson(String datePattern) {  
          JsonConfig jsonConfig = new JsonConfig();  
          jsonConfig.setExcludes(new String[]{""});  
          jsonConfig.setIgnoreDefaultExcludes(false);  
          jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);  
          jsonConfig.registerJsonValueProcessor(Date.class,  
              new DateJsonValueProcessor(datePattern));  
       
          return jsonConfig;  
      } 
 
  /** *//**
   *
   * @param excludes
   * @param datePattern
   * @return
   */
  public static JsonConfig configJson(String[] excludes,  
          String datePattern) {  
          JsonConfig jsonConfig = new JsonConfig();  
          jsonConfig.setExcludes(excludes);  
          jsonConfig.setIgnoreDefaultExcludes(false);  
          jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);  
          jsonConfig.registerJsonValueProcessor(Date.class,  
              new DateJsonValueProcessor(datePattern));  
       
          return jsonConfig;  
      } 
  
  public static Object parseJson(Object json){
  	
  	if(json instanceof JSONObject){
  		((JSONObject) json).get("sds");
  		return getMap4Json((JSONObject)json);
  	}
  	else if(json instanceof JSONArray){
  		return getList4Json((JSONArray)json);
  	}
  	else if(json instanceof String){
  		return (String)json;
  	}
  	else if(json instanceof Integer){
  		//return ((Integer)json).intValue();
  		return (Integer)json;
  	}
  	else if(json instanceof Float){
  		//return ((Float)json).floatValue();
  		return (Float)json;
  	}
  	else if(json instanceof Long){
  		//return ((Long)json).longValue();
  		return (Long)json;
  	}
  	else if(json instanceof Boolean){
  		//return ((Boolean)json).booleanValue();
  		return (Boolean)json;
  	}
  	else if(json instanceof Double){
  		//return ((Double)json).doubleValue();
  		return (Double)json;
  	}
  	else if(json instanceof JSONNull){
  		return null;
  	}
  	return json;
  }
  
}