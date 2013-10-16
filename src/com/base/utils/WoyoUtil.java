package com.base.utils;

import javax.servlet.http.HttpServletRequest;

import com.base.httpclient.HttpRemoteUtil;

public class WoyoUtil {
	/**
	 * 得到客户端IP
	 * 
	 * @return
	 */
	public static String getRealIP(HttpServletRequest request) {
	       String REALIP=request.getHeader("cdn-src-ip");
	       if(StringUtilsExtends.isBlank(REALIP)){
	           REALIP=request.getHeader("x-forwarded-for");
	           if(StringUtilsExtends.isNotBlank(REALIP)){
	              for(String IP:REALIP.split(",")){
	                  if(IP.startsWith("192.168")||IP.startsWith("10")||IP.startsWith("172.16")){
	                     continue;
	                  }else{
	                     REALIP=IP;
	                     break;
	                  }
	              }
	           }
	           if(StringUtilsExtends.isBlank(REALIP)){
	              REALIP=request.getHeader("x-real-ip");
	           }
	           if(StringUtilsExtends.isBlank(REALIP)){
	              REALIP=request.getRemoteAddr();
	           }
	       }
	       return REALIP;
	    }


	public static String getPlayKey(HttpServletRequest request) {

		return Md5Encrypt.md5(("WwHDmFaY" +getRealIP(request)));
	}
	public static void main(String[] args) {
		System.out.println(WoyoUtil.cleanCache("http://sports.woyo.com/video_2419_223.html"));
	}
	/**
	 * 清理缓存的接口
	 * @param urlStr
	 * @return ok表示成功
	 */
	public static String cleanCache(String sourcURL){
	 String salt="ILoveWOYO";
      String url=sourcURL+salt;
      String key=Md5Encrypt.md5(url);
      String cachepurge="";//Configuation.getConfigValue("cachepurgeURL");
      url=cachepurge+"?"+sourcURL+"&key="+key;
      String reslut=HttpRemoteUtil.remoteCall(url);
      return reslut;
	}
	
}
